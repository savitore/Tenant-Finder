package com.example.tenantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class Upload extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    private Button choose;
    private Button upload;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Uri uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask uploadtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        choose=findViewById(R.id.choosefile);
        upload=findViewById(R.id.upload);
        imageView=findViewById(R.id.imageview);
        progressBar=findViewById(R.id.progressBarimage);
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("Uploads");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadtask!=null && uploadtask.isInProgress())
                {
                    Toast.makeText(Upload.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadFile();
                }
            }
        });
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if(uri!=null)
        {
            StorageReference fileRef= storageReference.child(System.currentTimeMillis()+"."+ getFileExtension(uri));
            uploadtask=fileRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            },0);
                            Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String photoLink = uri.toString();
//                                    User1 user1=new User1(photoLink);
//                                    String modelid= databaseReference.push().getKey();
//                                    databaseReference.child(modelid).setValue(user1);
                                }
                            });

                            Toast.makeText(Upload.this,"Upload successful!",Toast.LENGTH_LONG).show();
                            Intent intent= new Intent(Upload.this,AddProperty.class);
                            startActivity(intent);
//                              Upload upload1=new Upload(filename.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
//                              String uploadid=databaseReference.push().getKey();
//                              databaseReference.child(uploadid).setValue(upload1);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Upload.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress =(100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });
        }
        else
        {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null)
        {
            uri=data.getData();
            imageView.setImageURI(uri);
        }
    }
}
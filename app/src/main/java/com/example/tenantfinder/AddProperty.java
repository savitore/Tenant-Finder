package com.example.tenantfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddProperty extends AppCompatActivity  {
    private static final String TAG = "Add Property";
    private static final String head="Type";
    private static final String desc="price";
    private static final String address="address";
    private static final String details="details";
    private static final String phone="phone";
    private EditText head1;
    private EditText desc1;
    private EditText address1;
    private EditText details1;
    private EditText phone1;
    Button button;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);
        head1=findViewById(R.id.editTextTextPersonName3);
        desc1=findViewById(R.id.editTextTextPersonName5);
        address1=findViewById(R.id.editTextTextPersonName4);
        details1=findViewById(R.id.editTextTextPersonName7);
        phone1=findViewById(R.id.editTextTextPersonName6);
        button=(Button) findViewById(R.id.btnAddImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProperty.this,Upload.class);
                startActivity(intent);
            }
        });

    }



    public void saveNote(View v)
    {
        String head2= head1.getText().toString();
        String desc2= desc1.getText().toString();
        String address2= address1.getText().toString();
        String details2= details1.getText().toString();
        String phone2= phone1.getText().toString();
        Map<String, Object> note= new HashMap<>();
        note.put(head,head2);
        note.put(desc,desc2);
        note.put(address,address2);
        note.put(details,details2);
        note.put(phone,phone2);

        if(head2.isEmpty())
        {
            head1.setError("Type of property is required");
            head1.requestFocus();
            return;
        }
        else if(address2.isEmpty())
        {
            address1.setError("Address is required");
            address1.requestFocus();
            return;
        }
        else if(desc2.isEmpty())
        {
            desc1.setError("Price is required");
            desc1.requestFocus();
            return;
        }
        else if(phone2.isEmpty())
        {
            phone1.setError("Phone number is required");
            phone1.requestFocus();
            return;
        }
        else if(!Patterns.PHONE.matcher(phone2).matches())
        {
            phone1.setError("Please enter correct number!");
            phone1.requestFocus();
            return;
        }
        else if(details2.isEmpty())
        {
            details1.setError("Enter some details");
            details1.requestFocus();
            return;
        }

        db.collection("Total Properties added").document(db.collection("Total Properties added").document().getId()).set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddProperty.this, "Property added!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddProperty.this,MainActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProperty.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });


    }


}
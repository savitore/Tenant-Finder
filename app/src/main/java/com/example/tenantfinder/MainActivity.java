package com.example.tenantfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ArrayList<User1> user1ArrayList,arrayList;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private ImageView button;
    private ImageButton menu1;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    private SearchView searchView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        imageView=(ImageView) findViewById(R.id.fav);
        menu1=(ImageButton) findViewById(R.id.menu);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_shadow_24));
//            }
//        });

        button= (ImageView) findViewById(R.id.addbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddProperty();
            }
        });
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acc= GoogleSignIn.getLastSignedInAccount(this);
        if(acc!=null)
        {
            String personname=acc.getDisplayName();
            String personemail=acc.getDisplayName();

        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=FirebaseFirestore.getInstance();
        user1ArrayList=new ArrayList<User1>();
        myAdapter= new MyAdapter(user1ArrayList,MainActivity.this);
        recyclerView.setAdapter(myAdapter);
        Fetchdata();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item1:
                Toast.makeText(this,"Loading..",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Loading..",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text)
    {
        List<ListItem> filteredlist = new ArrayList<>();
        for(ListItem item : user1ArrayList)
        {
            if(item.getHead().toLowerCase().contains(text))
            {
                filteredlist.add(item);
            }
        }
//        MyAdapter.filterlist(filteredlist);

    }

    private void Fetchdata()
    {
        db.collection("Total Properties added").orderBy("Type", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null)
                        {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }

                            Log.e("Firestore error",error.getMessage());
                            return;
                        }
                        for(DocumentChange dc: value.getDocumentChanges())
                        {
                            if(dc.getType()==DocumentChange.Type.ADDED)
                            {
                                user1ArrayList.add(dc.getDocument().toObject(User1.class));
                            }
                            myAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                        }
                    }
                });
    }

    public void openAddProperty()
    {
        Intent intent= new Intent(this,AddProperty.class);
        startActivity(intent);
    }

}
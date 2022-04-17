package com.example.tenantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    Button btnRegister2;
    EditText email;
    EditText password;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        btnRegister2=findViewById(R.id.btnRegister2);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        name=findViewById(R.id.name);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

    }
    public  void register(){
        String user1=email.getText().toString();
        String user2=name.getText().toString();
        String user3=password.getText().toString();
        if (user2.isEmpty()){
//            Toast.makeText(RegisterActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }
        if (user1.isEmpty()){
//            Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(user1).matches())
        {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }
        if (user3.isEmpty()){
//            Toast.makeText(RegisterActivity.this, "password is required", Toast.LENGTH_SHORT).show();
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(user3.length()<6)
        {
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(user1,user3)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user =new User(user2,user1);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Failed to register! Try again.", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try again.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
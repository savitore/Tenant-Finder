package com.example.tenantfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private Button forgo;
    private EditText foremail;
    private String email;
    private TextView back;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        back= (TextView) findViewById(R.id.forback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
        auth=FirebaseAuth.getInstance();
        foremail=findViewById(R.id.foremail);
        forgo=findViewById(R.id.forgo);

        forgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData()
    {
        email=foremail.getText().toString();
        if(email.isEmpty())
        {
             foremail.setError("Required");
        }
        else
        {
            forPass();
        }
    }

    private void forPass()
    {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPassword.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPassword.this,LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ForgetPassword.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openLogin()
    {
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
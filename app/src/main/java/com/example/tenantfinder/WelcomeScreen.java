package com.example.tenantfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeScreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        FirebaseAuth mUath = FirebaseAuth.getInstance();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Intent intent= new Intent(WelcomeScreen.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
                if(mUath.getCurrentUser() == null) {
                    Intent intent = new Intent(WelcomeScreen.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        },5000);
        lottieAnimationView=findViewById(R.id.lottie);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
    }
}
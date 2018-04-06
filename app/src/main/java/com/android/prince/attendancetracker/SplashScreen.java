package com.android.prince.attendancetracker;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    boolean checkAdminclick = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!checkAdminclick) {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();


                }
            }
        },5000);

        Button adminButton = (Button)findViewById(R.id.adminButtonAtSplash);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAdminclick = true;
                startActivity(new Intent(SplashScreen.this,AdminSection.class));
                finish();
            }
        });
    }
}

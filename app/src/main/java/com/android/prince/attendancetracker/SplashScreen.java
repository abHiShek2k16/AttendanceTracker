package com.android.prince.attendancetracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;


public class SplashScreen extends AppCompatActivity {

    boolean checkAdminclick = false;
    int clickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);

        if(NetworkCheck()){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!checkAdminclick) {
                        if(FirebaseAuth.getInstance().getCurrentUser() == null){
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            finish();
                        }else {
                            startActivity(new Intent(SplashScreen.this,StudentHomePage.class));
                            finish();
                        }
                    }
                }
            },5000);

            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.touchLayout);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clickCount == 5){
                        checkAdminclick = true;
                        startActivity(new Intent(SplashScreen.this,AdminPortal.class));
                        finish();
                    }else {
                        clickCount++;
                    }
                }
            });


        }else{

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

            dlgAlert.setMessage("unable to connect with server");
            dlgAlert.setTitle("Connectivity Error");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dlgAlert.setCancelable(false);
            dlgAlert.create().show();

        }
    }

    private boolean NetworkCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected()){
            return true;
        }
        else {
            return false;
        }
    }
}

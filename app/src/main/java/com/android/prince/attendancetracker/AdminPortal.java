package com.android.prince.attendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AdminPortal extends AppCompatActivity {


    String adminPass;

    RelativeLayout adminPassLayout;
    RelativeLayout adminPortalLayout;

    EditText adminPassText;

    Button adminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        adminPassLayout = (RelativeLayout)findViewById(R.id.adminPassword);
        adminPortalLayout = (RelativeLayout)findViewById(R.id.adminPortalLayout);
        adminLogin = (Button)findViewById(R.id.adminLoginButton);
        adminPassText = (EditText)findViewById(R.id.adminPortalCode);


        adminPortalLayout.setVisibility(View.VISIBLE);
        adminPassLayout.setVisibility(View.GONE);

        Button adminButton = (Button)findViewById(R.id.adminButtonAtSplash);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminPassLayout.setVisibility(View.VISIBLE);
                adminPortalLayout.setVisibility(View.INVISIBLE);
            }
        });

        Button teacher =  teacher = (Button)findViewById(R.id.teacherLoginButtonAtSplash);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPortal.this,TeacherLogin.class));
                finish();
            }
        });

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminPass = adminPassText.getText().toString();
                if(adminPass.equalsIgnoreCase("admin")){
                    startActivity(new Intent(AdminPortal.this,AdminSection.class));
                    finish();
                }else{
                    Toast.makeText(AdminPortal.this,"Not a valid code",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

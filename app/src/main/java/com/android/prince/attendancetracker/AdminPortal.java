package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPortal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        Button adminButton = (Button)findViewById(R.id.adminButtonAtSplash);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPortal.this,AdminSection.class));
                finish();
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
    }
}

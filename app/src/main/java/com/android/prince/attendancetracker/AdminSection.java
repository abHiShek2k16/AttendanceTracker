package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSection extends AppCompatActivity {

    Button addTeacherButton;
    Button addPaperButton;
    Button assignTeacherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_section);

        addTeacherButton = (Button)findViewById(R.id.addTeacherButtonAtAdminSection);
        addPaperButton = (Button)findViewById(R.id.addPaperButtonAtAdminSection);
        assignTeacherButton  = (Button)findViewById(R.id.assignClassButtonAtAdminSection);

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminSection.this,AddTeacherSection.class));

            }
        });

        addPaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminSection.this,AddPaperSection.class));

            }
        });

        assignTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminSection.this,AssignTeacherSection.class));

            }
        });
    }
}

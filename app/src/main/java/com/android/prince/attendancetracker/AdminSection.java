package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class AdminSection extends AppCompatActivity {

    Button addTeacherButton;
    Button addPaperButton;
    Button assignTeacherButton;
    Button sendNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_section);

        addTeacherButton = (Button)findViewById(R.id.addTeacherButtonAtAdminSection);
        addPaperButton = (Button)findViewById(R.id.addPaperButtonAtAdminSection);
        assignTeacherButton  = (Button)findViewById(R.id.assignClassButtonAtAdminSection);
        sendNotificationButton = (Button)findViewById(R.id.senNotificationButtonAtAdminSection);

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

        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSection.this,AdminNotification.class);
                intent.putExtra("VALUE","1");
                startActivity(intent);
                finish();
            }
        });
    }

    // Back Key Pressed
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exitByBackKey();

            return true;
        }

        return super.onKeyDown(keyCode,event);

    }
    private void exitByBackKey() {
       startActivity(new Intent(AdminSection.this,AdminPortal.class));
       finish();
    }
}

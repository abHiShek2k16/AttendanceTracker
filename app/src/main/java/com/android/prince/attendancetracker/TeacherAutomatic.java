package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class TeacherAutomatic extends AppCompatActivity {

    String sem;
    String dateStr;
    String name;
    String subject;

    TextView codeView;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_automatic);

        Intent intent = getIntent();
        sem = intent.getStringExtra("BRANCH");
        name = intent.getStringExtra("NAME");
        subject = intent.getStringExtra("SUBJECT");

        done = (Button)findViewById(R.id.doneAtTecherAutomatic);
        codeView = (TextView)findViewById(R.id.randomCodeAtTeacherAutomatic);

        String randCode = generateCode();

        codeView.setText(randCode);
        FirebaseDatabase.getInstance().getReference().child("AUTOMATICATT").child(subject).setValue(randCode);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("AUTOMATICATT").child(subject).setValue(false);

                Intent intent1 = new Intent(TeacherAutomatic.this,TeacherHomePage.class);
                intent1.putExtra("NAME",name);
                startActivity(intent1);
            }
        });
    }

    public String generateCode(){

        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@#$%&*";

        StringBuilder codeRandom = new StringBuilder();
        Random rnd = new Random();
        while (codeRandom.length() < 6) {
            int index = (int) (rnd.nextFloat() * text.length());
            codeRandom.append(text.charAt(index));
        }
        String saltStr = codeRandom.toString();

        return saltStr;
    }
}

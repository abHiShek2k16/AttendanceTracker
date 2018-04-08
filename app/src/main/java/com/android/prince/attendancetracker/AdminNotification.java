package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdminNotification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageButton send;
    EditText message;
    Spinner selectBranch;

    DatabaseReference databaseReference;

    String date;
    String branchSelected;

    private ArrayList<String> semArray = new ArrayList<>();

    String flag;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        Intent intent = getIntent();
        flag = intent.getStringExtra("VALUE");

        if(flag.equalsIgnoreCase("2")){
            name = intent.getStringExtra("NAME");
        }

        send = (ImageButton)findViewById(R.id.sendButtonAtAdminNotification);
        message = (EditText)findViewById(R.id.notificationAtAdminNotification);
        selectBranch = (Spinner)findViewById(R.id.semSpinnerAtAdminNotification);


        semArray.add("2K16IT");
        semArray.add("2K16CSE");
        semArray.add("2K16EEE");
        semArray.add("2K16ECE");
        semArray.add("2K16MECH");
        semArray.add("2K16CVL");
        semArray.add("2K16PROD");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AdminNotification.this,android.R.layout.simple_expandable_list_item_1,semArray);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectBranch.setAdapter(myAdapter);
        selectBranch.setOnItemSelectedListener(AdminNotification.this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd_MM_yyyy");
                date = mdformat.format(calendar.getTime());

                databaseReference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(branchSelected);
                databaseReference.child(date).setValue(message.getText().toString());

                if(flag.equalsIgnoreCase("1")){
                    startActivity(new Intent(AdminNotification.this,AdminSection.class));
                    finish();
                }else {
                    Intent intent = new Intent(AdminNotification.this,TeacherHomePage.class);
                    intent.putExtra("NAME",name);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        branchSelected = semArray.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

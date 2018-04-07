package com.android.prince.attendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignTeacherSection extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner teacherName;
    Spinner sem;
    Spinner paper;

    Button assignTeacher;

    String teacherNameStr;
    String semStr;
    String paperNameStr;

    ArrayList<String> teacherNameArray = new ArrayList<>();
    ArrayList<String> paperNameArray = new ArrayList<>();
    ArrayList<String> semArray = new ArrayList<>();

    DatabaseReference databaseReferenceToTeacher;
    DatabaseReference databaseReferenceToPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_teacher_section);

        teacherName = (Spinner)findViewById(R.id.teacherNameSpinnerAtAssignTeacherSection);
        sem = (Spinner)findViewById(R.id.semSpinnerAtAssignTeacherSection);
        paper = (Spinner)findViewById(R.id.paperSpinnerAtAssignTeacherSection);

        assignTeacher = (Button)findViewById(R.id.assignTeacherButtonAtAssignTeacher);

        databaseReferenceToPaper = FirebaseDatabase.getInstance().getReference().child("PAPER");
        databaseReferenceToTeacher = FirebaseDatabase.getInstance().getReference().child("TEACHER").child("ASSIGNED");

        new LoadTeacherName().execute(databaseReferenceToTeacher);
        new LoadPaperName().execute(databaseReferenceToPaper);

        semArray.add("2k16IT");
        semArray.add("2k16CSE");
        semArray.add("2k16EEE");
        semArray.add("2k16ECE");
        semArray.add("2k16MECH");
        semArray.add("2k16CVL");
        semArray.add("2k16PROD");

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AssignTeacherSection.this,android.R.layout.simple_expandable_list_item_1,semArray);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(myAdapter);
        sem.setOnItemSelectedListener(AssignTeacherSection.this);

        assignTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacherNameStr != null && paperNameStr != null && semStr != null){

                    FirebaseDatabase.getInstance().getReference().child("TEACHER").child("ASSIGNED").child(teacherNameStr).child(paperNameStr).setValue(semStr);
                    startActivity(new Intent(AssignTeacherSection.this,AdminSection.class));
                    finish();
                }else {
                    Toast.makeText(AssignTeacherSection.this,"Please choose all the details ...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.teacherNameSpinnerAtAssignTeacherSection) {
            teacherNameStr = teacherNameArray.get(i);
        }
        else if(spinner.getId() == R.id.semSpinnerAtAssignTeacherSection) {
           semStr = semArray.get(i);
        }else if(spinner.getId() == R.id.paperSpinnerAtAssignTeacherSection){
           paperNameStr = paperNameArray.get(i);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class LoadTeacherName extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        teacherNameArray.add(child.getKey());
                    }

                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AssignTeacherSection.this,android.R.layout.simple_expandable_list_item_1,teacherNameArray);
                    myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    teacherName.setAdapter(myAdapter);
                    teacherName.setOnItemSelectedListener(AssignTeacherSection.this);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }


    public class LoadPaperName extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        paperNameArray.add(child.getKey());
                    }

                    ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AssignTeacherSection.this,android.R.layout.simple_expandable_list_item_1,paperNameArray);
                    myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    paper.setAdapter(myAdapter);
                    paper.setOnItemSelectedListener(AssignTeacherSection.this);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }
}

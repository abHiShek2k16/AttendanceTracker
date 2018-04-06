package com.android.prince.attendancetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_teacher_section);

        teacherName = (Spinner)findViewById(R.id.teacherNameSpinnerAtAssignTeacherSection);
        sem = (Spinner)findViewById(R.id.semSpinnerAtAssignTeacherSection);
        paper = (Spinner)findViewById(R.id.paperSpinnerAtAssignTeacherSection);

        assignTeacher = (Button)findViewById(R.id.assignTeacherButtonAtAssignTeacher);

        /*

             ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(SoldActivity.this,android.R.layout.simple_expandable_list_item_1,teamName);
             myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
             spinner.setAdapter(myAdapter);
             spinner.setOnItemSelectedListener(SoldActivity.this);

        */

        assignTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}

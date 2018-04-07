package com.android.prince.attendancetracker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.prince.attendancetracker.adapter.StudentAttendanceAdapter;
import com.android.prince.attendancetracker.adapter.TeacherPaperAdapter;
import com.android.prince.attendancetracker.javaClass.combine;
import com.android.prince.attendancetracker.listener.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentHomePage extends AppCompatActivity {

    String uId;
    String rollNo;
    String branchCode;

    DatabaseReference databaseReferenceToStudentDetail;

    ArrayList<String> myPaper = new ArrayList<>();
    ArrayList<String> percent = new ArrayList<>();
    ArrayList<Drawable> image = new ArrayList<>();

    ArrayList<String> present = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    ArrayList<String> countPresent = new ArrayList<>();

    int count = 0;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceToStudentDetail = FirebaseDatabase.getInstance().getReference().child("STUDENT").child("TOTAL").child(uId);
        new LoadStudentDetail().execute(databaseReferenceToStudentDetail);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtStudentHomePage);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentHomePage.this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(StudentHomePage.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       Intent intent = new Intent(StudentHomePage.this,ShowAttendance.class);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public class LoadStudentDetail extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                       if(child.getKey().equalsIgnoreCase("ROLLNO")){
                           rollNo = child.getValue().toString();
                       }else if(child.getKey().equalsIgnoreCase("BRANCHCODE")){
                           branchCode = child.getValue().toString();
                       }
                    }

                    new LoadStudentPaper().execute(FirebaseDatabase.getInstance().getReference().child("STUDENT").child("LIST").child(branchCode).child(rollNo).child("REGISTEREDPAPER"));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    public class LoadStudentPaper extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        myPaper.add(child.getKey());
                    }

                    if(myPaper.size() == 0){

                    }else{
                       for(int i=0;i<myPaper.size();i++){
                           new LoadAttendance().execute(ref.child(myPaper.get(i)));
                       }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

    public class LoadAttendance extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    count++;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue().toString().equalsIgnoreCase("true")) {
                            countPresent.add("1");
                        }

                        present.add(child.getValue().toString());
                        date.add(child.getKey());
                    }

                    percent.add(String.valueOf(((countPresent.size())*100)/date.size()));

                  /*  if(percentage.get(count) >= 75){
                        image.add(getResources().getDrawable(R.drawable.green));
                    }else{
                        image.add(getResources().getDrawable(R.drawable.red));
                    }   */



                    StudentAttendanceAdapter adapter = new StudentAttendanceAdapter(StudentHomePage.this,myPaper,percent,image);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}

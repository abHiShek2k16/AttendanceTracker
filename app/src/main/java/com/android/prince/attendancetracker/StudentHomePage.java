package com.android.prince.attendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    int count = 0;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBarAtStudentHomePage);
        setSupportActionBar(toolbar);

        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReferenceToStudentDetail = FirebaseDatabase.getInstance().getReference().child("STUDENT").child("TOTAL").child(uId);
        new LoadStudentDetail().execute(databaseReferenceToStudentDetail);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtStudentHomePage);
        recyclerView.setLayoutManager(new LinearLayoutManager(StudentHomePage.this));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(StudentHomePage.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       Intent intent = new Intent(StudentHomePage.this,ShowAttendance.class);
                       intent.putExtra("PAPER",myPaper.get(position));
                       intent.putExtra("UID",uId);
                       intent.putExtra("ROLLNO",rollNo);
                       intent.putExtra("BRANCH",branchCode);
                       startActivity(intent);
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
                    int flag = 0;
                    int totalClasses = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue().toString().equalsIgnoreCase("true")) {
                            flag++;
                        }
                        totalClasses++;
                    }

                    percent.add(String.valueOf((flag*100)/totalClasses));

                    if(Double.parseDouble(percent.get(count-1)) >= 75){
                        image.add(getResources().getDrawable(R.drawable.green));
                    }else{
                        image.add(getResources().getDrawable(R.drawable.redcircler));
                    }

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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.notification:
                Intent intent = new Intent(StudentHomePage.this,ViewNotification.class);
                intent.putExtra("BRANCH",branchCode);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }

        return true;
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
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("Do You Want To Exit").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }
}

package com.android.prince.attendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.android.prince.attendancetracker.adapter.TeacherPaperAdapter;
import com.android.prince.attendancetracker.listener.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherHomePage extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<String> nameArray = new ArrayList<>();
    ArrayList<String> semArray = new ArrayList<>();

    DatabaseReference databaseReference;

    String userNameStr;

    FloatingActionButton sendNotificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home_page);

        try{
            Intent intent = getIntent();
            userNameStr = intent.getStringExtra("NAME");
        }catch (Exception e){

        }

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtTeacherHomePage);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeacherHomePage.this));

        sendNotificationButton = (FloatingActionButton)findViewById(R.id.sendNotificationAtTeacherHomePage);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TEACHER").child("ASSIGNED").child(userNameStr);
        new LoadNameAndSem().execute(databaseReference);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(TeacherHomePage.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(TeacherHomePage.this,UpdateAttendance.class);
                        intent.putExtra("BRANCH",semArray.get(position));
                        intent.putExtra("SUBJECT",nameArray.get(position));
                        intent.putExtra("NAME",userNameStr);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherHomePage.this,AdminNotification.class);
                intent.putExtra("VALUE","2");
                intent.putExtra("NAME",userNameStr);
                startActivity(intent);
                finish();
            }
        });


    }

    public class LoadNameAndSem extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        nameArray.add(child.getKey());
                        semArray.add(child.getValue().toString());
                    }

                    if(nameArray != null && semArray != null) {
                        TeacherPaperAdapter adapter = new TeacherPaperAdapter(TeacherHomePage.this, nameArray, semArray);
                        recyclerView.setAdapter(adapter);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
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

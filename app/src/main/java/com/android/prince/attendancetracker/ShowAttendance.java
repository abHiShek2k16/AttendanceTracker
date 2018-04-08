package com.android.prince.attendancetracker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.prince.attendancetracker.adapter.DateAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAttendance extends AppCompatActivity {

    String myPaper;
    String uId;
    String rollNo;
    String branchCode;

    ArrayList<Drawable> image = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();

    RecyclerView recyclerView;

    TextView textView;

    DatabaseReference databaseReferenceToStudentDetail;

    ImageView attendance;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        textView = (TextView)findViewById(R.id.toolBarTextAtShowAtt);

        Intent intent = getIntent();
        myPaper = intent.getStringExtra("PAPER");
        uId = intent.getStringExtra("UID");
        rollNo = intent.getStringExtra("ROLLNO");
        branchCode = intent.getStringExtra("BRANCH");

        progressBar = (ProgressBar)findViewById(R.id.progressBarAtShowAttendance);

        textView.setText(myPaper);

        databaseReferenceToStudentDetail = FirebaseDatabase.getInstance().getReference().child("STUDENT").child("LIST").child(branchCode).child(rollNo).child("REGISTEREDPAPER").child(myPaper);
        new LoadClassAtt().execute(databaseReferenceToStudentDetail);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtShowAttendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowAttendance.this));

        attendance = (ImageView)findViewById(R.id.renewAtShowAttendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAttendance.this,StudentAutomatic.class);
                intent.putExtra("PAPER",myPaper);
                intent.putExtra("UID",uId);
                intent.putExtra("ROLLNO",rollNo);
                intent.putExtra("BRANCH",branchCode);
                startActivity(intent);
                finish();
            }
        });
    }

    public class LoadClassAtt extends AsyncTask<DatabaseReference,Void,Void> {

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        date.add(child.getKey());
                        if(child.getValue().toString().equalsIgnoreCase("true")){
                            image.add(getResources().getDrawable(R.drawable.green));
                        }else{
                            image.add(getResources().getDrawable(R.drawable.redcircler));
                        }
                    }

                    DateAdapter adapter = new DateAdapter(ShowAttendance.this,date,image);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }
}

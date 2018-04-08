package com.android.prince.attendancetracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.prince.attendancetracker.adapter.NotificationAdapter;
import com.android.prince.attendancetracker.adapter.StudentAttendanceAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewNotification extends AppCompatActivity {

    String branchCode;

    RecyclerView recyclerView;

    DatabaseReference databaseReference;

    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> notice = new ArrayList<>();

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);

        progressBar = (ProgressBar)findViewById(R.id.progressBarAtViewNotification);

        branchCode = getIntent().getStringExtra("BRANCH");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(branchCode);
        new LoadNotice().execute(databaseReference);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtNotice);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewNotification.this));
    }

    public class LoadNotice extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                       notice.add(child.getValue().toString());
                       date.add(child.getKey());
                    }

                    progressBar.setVisibility(View.GONE);
                    NotificationAdapter adapter = new NotificationAdapter(ViewNotification.this,date,notice);
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

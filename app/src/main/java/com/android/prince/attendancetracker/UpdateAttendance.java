package com.android.prince.attendancetracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.prince.attendancetracker.adapter.TeacherPaperAdapter;
import com.android.prince.attendancetracker.adapter.UpdateAttandanceAdapter;
import com.android.prince.attendancetracker.listener.RecyclerItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateAttendance extends AppCompatActivity {

    String sem;
    String dateStr;
    String name;
    String subject;


    EditText date;
    Button update;
    RecyclerView recyclerView;

    DatabaseReference databaseReference;

    private ArrayList<String> rollNo = new ArrayList<>();
    boolean[] flag = new boolean[60];

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_attendance);

        update = (Button)findViewById(R.id.updateButtonAtUpdateAttendance);

        update.setEnabled(false);

        Intent intent = getIntent();
        sem = intent.getStringExtra("BRANCH");
        name = intent.getStringExtra("NAME");
        subject = intent.getStringExtra("SUBJECT");

        for(int i=0;i<60;i++){
            flag[i] = false;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("STUDENT").child("LIST").child(sem);
        new LoadRoll().execute(databaseReference);

        date = (EditText)findViewById(R.id.datePickerAtUpdateAttandance);
        update = (Button)findViewById(R.id.updateButtonAtUpdateAttendance);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewAtUpdateAttandance);
        recyclerView.setLayoutManager(new LinearLayoutManager(UpdateAttendance.this));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(UpdateAttendance.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateStr = dayOfMonth + "_" + (monthOfYear + 1) + "_" + year;
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  dateStr = date.getText().toString();
                for(int i=0;i<rollNo.size();i++){
                    databaseReference.child(rollNo.get(i)).child("REGISTEREDPAPER").child(subject).child(dateStr).setValue(flag[i]);
                }
                Intent intent1 = new Intent(UpdateAttendance.this,TeacherHomePage.class);
                intent1.putExtra("NAME",name);
                startActivity(intent1);
                finish();
            }
        });

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(UpdateAttendance.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       if(flag[position]){
                           flag[position] = false;
                       }else{
                           flag[position] = true;
                       }
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public class LoadRoll extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                       rollNo.add(child.getKey());
                    }

                    UpdateAttandanceAdapter updateAdapter = new UpdateAttandanceAdapter(UpdateAttendance.this, rollNo);
                    recyclerView.setAdapter(updateAdapter);

                    update.setEnabled(true);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }
}

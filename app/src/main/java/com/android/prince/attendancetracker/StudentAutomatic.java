package com.android.prince.attendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentAutomatic extends AppCompatActivity {

    String myPaper;
    String uId;
    String rollNo;
    String branchCode;

    String date;

    EditText code;
    Button done;

    String codeFind;
    String codeEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_automatic);

        Intent intent = getIntent();
        myPaper = intent.getStringExtra("PAPER");
        uId = intent.getStringExtra("UID");
        rollNo = intent.getStringExtra("ROLLNO");
        branchCode = intent.getStringExtra("BRANCH");

        code = (EditText)findViewById(R.id.editTextCodeAtStudentAutomatic);
        done = (Button)findViewById(R.id.donButtonAtStudentAutomatic);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.getText().toString() != null) {

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("dd_MM_yyyy");
                    date = mdformat.format(calendar.getTime());

                    codeEnter = code.getText().toString();
                    new LoadCode().execute(FirebaseDatabase.getInstance().getReference().child("AUTOMATICATT"));
                }else {
                    Toast.makeText(StudentAutomatic.this,"Please Enter code",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class LoadCode extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference ref = databaseReferences[0];
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.getKey().equalsIgnoreCase(myPaper)){
                            codeFind = child.getValue().toString();
                            break;
                        }
                    }

                    if(codeFind.equalsIgnoreCase(codeEnter) && !(codeFind.equalsIgnoreCase("false"))){

                        FirebaseDatabase.getInstance().getReference().child("STUDENT").child("LIST")
                                .child(branchCode).child(rollNo).child("REGISTEREDPAPER")
                                .child(myPaper).child(date)
                                .setValue(true);

                        Intent intent = new Intent(StudentAutomatic.this,ShowAttendance.class);
                        intent.putExtra("PAPER",myPaper);
                        intent.putExtra("UID",uId);
                        intent.putExtra("ROLLNO",rollNo);
                        intent.putExtra("BRANCH",branchCode);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(StudentAutomatic.this,"Please Enter valid code",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }
    }

}

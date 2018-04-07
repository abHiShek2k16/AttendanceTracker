package com.android.prince.attendancetracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AddPaperSection extends AppCompatActivity {

    TextInputEditText paperName;
    TextInputEditText paperCode;
    TextInputEditText paperCredit;
    TextInputEditText paperSem;

    Button addPaper;

    String paperNameStr;
    String paperSemStr;

    DatabaseReference databaseReferenceToStuduent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paper_section);

        paperName = (TextInputEditText)findViewById(R.id.paperNameEditTextAtAddPaperSection);
        paperSem = (TextInputEditText)findViewById(R.id.semEditTextAtAddPaperSection);

        addPaper = (Button)findViewById(R.id.addPaperButtonAtAdminSection);

        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paperNameStr = paperName.getText().toString();
                paperSemStr = paperSem.getText().toString();

                databaseReferenceToStuduent = FirebaseDatabase.getInstance().getReference().child("STUDENT").child("LIST").child(paperSemStr);

                FirebaseDatabase.getInstance().getReference("PAPER").child(paperNameStr).setValue(paperSemStr);

                new LoadStudent().execute(databaseReferenceToStuduent);
            }
        });
    }

    public class LoadStudent extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {

            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        databaseReference.child(child.getKey()).child("REGISTEREDPAPER").child(paperNameStr).setValue(true);
                    }
                    startActivity(new Intent(AddPaperSection.this,AdminSection.class));
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

            return null;
        }
    }
}

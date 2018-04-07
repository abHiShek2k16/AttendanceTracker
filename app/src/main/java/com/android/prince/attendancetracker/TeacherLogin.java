package com.android.prince.attendancetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherLogin extends AppCompatActivity {

    EditText userCode;

    Button login;

    DatabaseReference databaseReference;

    boolean flag = false;

    String userCodeStr;
    String userNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        userCode = (EditText)findViewById(R.id.userNameAtTeacherLogin);
        login = (Button)findViewById(R.id.loginButtonAtTeacherLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("TEACHER").child("TOTAL");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCodeStr = userCode.getText().toString();
                if(userCodeStr.equalsIgnoreCase("")){
                    Toast.makeText(TeacherLogin.this,"Please Enter the code",Toast.LENGTH_SHORT).show();
                }else{
                    new MatchUser().execute(databaseReference);
                }
            }
        });
    }

    public class MatchUser extends AsyncTask<DatabaseReference,Void,Void>{

        @Override
        protected Void doInBackground(DatabaseReference... databaseReferences) {
            final DatabaseReference databaseReference = databaseReferences[0];

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                       if(child.getKey().equalsIgnoreCase(userCodeStr)){
                               userNameStr = child.getValue().toString();
                               flag = true;
                               break;
                       }
                    }

                    if(flag){
                        Intent intent = new Intent(TeacherLogin.this,TeacherHomePage.class);
                        intent.putExtra("NAME",userNameStr);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(TeacherLogin.this,"Enter valid Detail ... ",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(TeacherLogin.this,AdminPortal.class));
        finish();
    }
}

package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPage extends AppCompatActivity {

    Button signUp;

    EditText name;
    EditText sem;
    EditText rollNo;
    EditText password;
    EditText email;
    EditText branch;


    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        signUp = (Button)findViewById(R.id.signUpButtonAtSignUp);

        name = (EditText)findViewById(R.id.nameEditTextAtSignUp);
        sem = (EditText)findViewById(R.id.semEditTextAtSignUp);
        rollNo = (EditText)findViewById(R.id.rollEditTextAtSignUp);
        password = (EditText)findViewById(R.id.passwordEditTextAtSignUp);
        email = (EditText)findViewById(R.id.emailEditTextAtSignUp);
        branch = (EditText)findViewById(R.id.branchEditTextAtSignUp);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("STUDENT");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mFirebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpPage.this, "HI!! please fill all the above detail correctly...", Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            DatabaseReference databaseSetRef = databaseReference.child(sem.getText().toString()).child(rollNo.getText().toString());
                            databaseSetRef.child("EMAIL").setValue(email.getText().toString());
                            databaseSetRef.child("NAME").setValue(name.getText().toString());
                            databaseSetRef.child("ROLL").setValue(rollNo.getText().toString());
                            databaseSetRef.child("SEM").setValue(sem.getText().toString());

                            startActivity(new Intent(SignUpPage.this, MainActivity.class));
                            Toast.makeText(SignUpPage.this,"Succesfully SignUp....",Toast.LENGTH_LONG).show();
                        } else {

                        }
                    }
                });


            }
        });
    }
}

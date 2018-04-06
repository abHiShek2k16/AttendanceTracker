package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
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

public class MainActivity extends AppCompatActivity {

    EditText userName;
    EditText password;

    Button login;
    Button signUp;
    Button teacher;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText)findViewById(R.id.userNameAtActivityMain);
        password = (EditText)findViewById(R.id.passwordAtActivityMain);

        login = (Button)findViewById(R.id.loginButtonAtActivityMain);
        signUp = (Button)findViewById(R.id.signUpButtonAtActivityMain);
        teacher = (Button)findViewById(R.id.teacherLoginButtonAtActivityMain);

        mAuth = FirebaseAuth.getInstance();

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,TeacherLogin.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpPage.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(userName.getText().toString(),password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Enter correct Detail for Continue !!!",Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           startActivity(new Intent(MainActivity.this,StudentHomePage.class));
                           finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Login Failed ...!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}

package com.android.prince.attendancetracker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView signUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText)findViewById(R.id.userNameAtActivityMain);
        password = (EditText)findViewById(R.id.passwordAtActivityMain);

        login = (Button)findViewById(R.id.loginButtonAtActivityMain);
        signUp = (TextView) findViewById(R.id.signUpButtonAtActivityMain);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignUpPage.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!(userName.getText().toString().isEmpty() && password.getText().toString().isEmpty())){

                   mAuth.signInWithEmailAndPassword(userName.getText().toString(),password.getText().toString()).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(MainActivity.this,"Enter correct Detail to Continue !!!",Toast.LENGTH_LONG).show();
                       }
                   }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               startActivity(new Intent(MainActivity.this,StudentHomePage.class));
                               finish();
                           }
                           else{
                               Toast.makeText(MainActivity.this,"Incorrect password ...!!!",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

               }else{
                   Toast.makeText(MainActivity.this,"Please fill all the detail ...",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}

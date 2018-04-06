package com.android.prince.attendancetracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    TextInputEditText userName;
    TextInputEditText password;

    Button login;
    Button signUp;
    Button teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (TextInputEditText)findViewById(R.id.userNameAtActivityMain);
        password = (TextInputEditText)findViewById(R.id.passwordAtActivityMain);

        login = (Button)findViewById(R.id.loginButtonAtActivityMain);
        signUp = (Button)findViewById(R.id.signUpButtonAtActivityMain);
        teacher = (Button)findViewById(R.id.teacherLoginButtonAtActivityMain);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

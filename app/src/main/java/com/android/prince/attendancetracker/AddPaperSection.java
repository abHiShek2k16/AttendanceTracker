package com.android.prince.attendancetracker;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AddPaperSection extends AppCompatActivity {

    TextInputEditText paperName;
    TextInputEditText paperCode;
    TextInputEditText paperCredit;
    TextInputEditText paperSem;

    Button addPaper;

    String paperNameStr;
    String paperCodeStr;
    String paperCreditStr;
    String paperSemStr;

    DatabaseReference databaseReferenceToStuduent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_paper_section);

        paperName = (TextInputEditText)findViewById(R.id.paperNameEditTextAtAddPaperSection);
        paperCode = (TextInputEditText)findViewById(R.id.paperCodeEditTextAtAddPaperSection);
        paperCredit = (TextInputEditText)findViewById(R.id.paperCreditEditTextAtAddPaperSection);
        paperSem = (TextInputEditText)findViewById(R.id.semEditTextAtAddPaperSection);

        addPaper = (Button)findViewById(R.id.addPaperButtonAtAdminSection);

        databaseReferenceToStuduent = FirebaseDatabase.getInstance().getReference();

        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paperNameStr = paperName.getText().toString();
                paperCodeStr = paperCode.getText().toString();
                paperSemStr = paperSem.getText().toString();
                paperCreditStr = paperCredit.getText().toString();


            }
        });
    }
}

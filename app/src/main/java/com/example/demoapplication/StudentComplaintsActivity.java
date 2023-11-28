package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentComplaintsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        Button btnNewComplaint = findViewById(R.id.studentNewComplaintButton);
        Button btnComplaintsBack = findViewById(R.id.studentComplaintsBackButton);

        btnNewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to write new complaint page
                startActivity(new Intent(StudentComplaintsActivity.this, StudentNewComplaintActivity.class));
                finish();
            }
        });

        btnComplaintsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to student page
                startActivity(new Intent(StudentComplaintsActivity.this, StudentActivity.class));
                finish();
            }
        });
    }

}

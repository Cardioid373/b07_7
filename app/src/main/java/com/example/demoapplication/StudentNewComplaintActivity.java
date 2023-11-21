package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentNewComplaintActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);

        Button btnNewComplaintBack = findViewById(R.id.newComplaintBackButton);

        btnNewComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to complaints page
                startActivity(new Intent(StudentNewComplaintActivity.this, StudentComplaintsActivity.class));
            }
        });
    }
}

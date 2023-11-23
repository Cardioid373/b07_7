package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StudentPastComplaintsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_complaints);

        Button btnPastComplaintsBack = findViewById(R.id.studentPastComplaintsBackButton);

        btnPastComplaintsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Return to complaints page
                startActivity(new Intent(StudentPastComplaintsActivity.this, StudentComplaintsActivity.class));
                finish();
            }
        });
    }
}

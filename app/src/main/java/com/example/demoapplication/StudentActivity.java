package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Button btnLogout = findViewById(R.id.logoutButton);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to login page
                startActivity(new Intent(StudentActivity.this, MainActivity.class));
                Toast.makeText(StudentActivity.this, "Logout successful!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
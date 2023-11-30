package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {
    public static String currUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnPostAnnouncement = findViewById(R.id.btnPostAnnouncement);
        Button btnEvents = findViewById(R.id.btnEvents);
        Button btnComplaints = findViewById(R.id.btnComplaints);
        Button btnLogout = findViewById(R.id.btnlogOut);
        TextView topText = findViewById(R.id.topText);
        topText.setText("Welcome back " + MainActivity.currentUser + "!");

        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, CreateEventActivity.class));
            }
        });

        btnPostAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, WriteAnnouncementActivity.class));
            }
        });
        btnComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminHomeActivity.this, ComplaintActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, MainActivity.class));
                finish();
                Toast.makeText(AdminHomeActivity.this, "Logout successful!", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnFeedback = findViewById(R.id.EventFeedback);

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomeActivity.this, EventFeedbackActivity.class));
            }
        });
    }
    public void goToCreateEventPage(View view) {
        startActivity(new Intent(this, CreateEventActivity.class));
    }
}

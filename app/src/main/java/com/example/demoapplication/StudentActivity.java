package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    public static String currUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        TextView topText = findViewById(R.id.topText);
        topText.setText("Welcome back " + LoginActivityView.currentUser + "!");
        Button btnComplaints = findViewById(R.id.studentComplaintsButton);
        Button btnLogout = findViewById(R.id.studentLogoutButton);
        Button btnPost = findViewById(R.id.studentPostButton);
	    Button btnEvents = findViewById(R.id.studentEventsButton);
        Button btnNotifications = findViewById(R.id.studentNotificationsButton);
		
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to post page
                startActivity(new Intent(StudentActivity.this, PostActivity.class));
                finish();
            }
        });

        btnComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to complaints page
                startActivity(new Intent(StudentActivity.this, StudentNewComplaintActivity.class));
                finish();
            }
        });

        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to events page
                startActivity(new Intent(StudentActivity.this, EventsActivity.class));
                finish();
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Jump to notifications page
                startActivity(new Intent(StudentActivity.this, NotificationActivity.class));
                finish();
            }
        });
	    
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to login page
                startActivity(new Intent(StudentActivity.this, LoginActivityView.class));
                finish();
                Toast.makeText(StudentActivity.this, "Logout successful!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

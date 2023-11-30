package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button btnAnnouncements = findViewById(R.id.btnEvents);
        Button btnPostAnnouncement = findViewById(R.id.btnPostAnnouncement);
        btnAnnouncements.setOnClickListener(new View.OnClickListener() {
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
    }
    public void goToCreateEventPage(View view) {
        startActivity(new Intent(this, CreateEventActivity.class));
    }
}
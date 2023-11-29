package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);

        Button btnNotificationBack2 = findViewById(R.id.NotificationBackButton2);

        Intent intent = getIntent();
        String notificationTitle = intent.getStringExtra("notificationTitle");
        String notificationContent = intent.getStringExtra("notificationContent");

        TextView titleView = findViewById(R.id.notificationTitle);
        TextView contentView = findViewById(R.id.notificationContent);

        titleView.setText(notificationTitle);
        contentView.setText(notificationContent);

        btnNotificationBack2.setOnClickListener(view -> {
            //Return to student pag
            startActivity(new Intent(NotificationActivity2.this, NotificationActivity.class));
            finish();
        });
    }
}
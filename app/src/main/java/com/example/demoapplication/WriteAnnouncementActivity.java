package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WriteAnnouncementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_announcement);

        EditText editAnnouncementTitle = findViewById(R.id.editAnnouncementTitle);
        EditText editAnnouncementBody = findViewById(R.id.editAnnouncementBody);
        Button announcementPostBtn = findViewById((R.id.announcementPostBtn));
        Button announcementBackBtn = findViewById(R.id.announcementBackBtn);

        announcementPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());

                String currentDate = dateFormat.format(calendar.getTime());
                String currentTime = timeFormat.format(calendar.getTime());

                String title = editAnnouncementTitle.getText().toString();
                String body = editAnnouncementBody.getText().toString();
            }
        });

        announcementBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to student page
                startActivity(new Intent(WriteAnnouncementActivity.this, AdminHomeActivity.class));
                finish();
            }
        });
    }
}

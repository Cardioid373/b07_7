package com.example.demoapplication;

import android.content.Intent;
import java.util.Calendar;
import java.util.Date;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CreateEventActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private EditText editTextMaxLimit;
    private EditText editTextDepartment;

    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextMaxLimit = findViewById(R.id.editTextMaxLimit);
        editTextDepartment = findViewById(R.id.editTextDepartment);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        Button buttonPickDate = findViewById(R.id.buttonPickDate);
        Button buttonPickTime = findViewById(R.id.buttonPickTime);

        buttonPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(datePicker);
            }
        });

        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(timePicker);
            }
        });

        Button buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateEventActivity.this, AdminHomeActivity.class));
                finish();
            }
        });
    }

    private void createEvent() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        Date currentTime = Calendar.getInstance().getTime();
        String currTime = currentTime.toString();

        String selectedDate = String.format("%04d-%02d-%02d", year, month, day);
        String selectedTime = String.format("%02d:%02d", hour, minute);
        Event event = new Event(
                editTextName.getText().toString(), selectedDate, selectedTime, editTextLocation.getText().toString(),
                editTextDepartment.getText().toString(), editTextDescription.getText().toString(), Integer.parseInt(editTextMaxLimit.getText().toString()), 0, 0, currTime

        );

        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");

        eventsRef.orderByChild("name").equalTo(event.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(CreateEventActivity.this, "Event with the same name already exists", Toast.LENGTH_SHORT).show();
                } else {
                    eventsRef.child(event.getName()).setValue(event);
                    Toast.makeText(CreateEventActivity.this, "Event created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CreateEventActivity", "Database Error: " + databaseError.getMessage());
                Toast.makeText(CreateEventActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
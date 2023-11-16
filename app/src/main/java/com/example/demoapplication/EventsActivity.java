package com.example.demoapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    FirebaseDatabase db;

    private TextView eventName;
    private TextView eventDepartment;
    private TextView eventLocation;
    private TextView eventTime;
    private TextView eventDescription;

    ArrayList<String> eventNames;
    ArrayList<String> eventDepartments;
    ArrayList<String> eventLocations;
    ArrayList<String> eventTimes;
    ArrayList<String> eventDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        db = FirebaseDatabase.getInstance("https://b07data-default-rtdb.firebaseio.com/");

        eventName = (TextView) findViewById(R.id.eventName);
        eventDepartment = (TextView) findViewById(R.id.eventDepartment);
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventTime = (TextView) findViewById(R.id.eventTime);
        eventDescription = (TextView) findViewById(R.id.eventDescription);

        // this section is meant to collect the info of the events into multiple arrays
        DatabaseReference eventRef = db.getReference().child("events");
        eventRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Get details from snapshot
                        eventNames = collectEventDetails(snapshot, "name");
                        eventDepartments = collectEventDetails(snapshot, "department");
                        eventLocations = collectEventDetails(snapshot, "location");
                        eventTimes = collectEventDetails(snapshot, "time");
                        eventDescriptions = collectEventDetails(snapshot, "description");
                        updateActivityText();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // space to handle error (will look into this later)
                    }
                }

        );

    }

    @NonNull
    private ArrayList<String> collectEventDetails(DataSnapshot events, String detail) {
        ArrayList<String> list = new ArrayList<String>();
        for (DataSnapshot snapshot : events.getChildren()) {
            list.add(snapshot.child(detail).getValue(String.class));
        }
        return list;
    }

    private void updateActivityText() {
        eventName.setText(eventNames.get(0));
        eventDepartment.setText(eventDepartments.get(0));
        eventLocation.setText(eventLocations.get(0));
        eventTime.setText(eventTimes.get(0));
        eventDescription.setText(eventDescriptions.get(0));
    }

}
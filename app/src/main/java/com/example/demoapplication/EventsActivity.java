package com.example.demoapplication;

import static com.example.demoapplication.MainActivity.currentUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference eventsRef;

    private TextView eventName;
    private TextView eventDepartment;
    private TextView eventLocation;
    private TextView eventDateTime;
    private TextView eventDescription;
    private SwitchCompat attendSwitch;
    private SwitchCompat rsvpSwitch;
    private Button leftButton;
    private Button rightButton;
    private Button backButton;
    private RatingBar reviewRatingBar;
    private EditText reviewEditText;

    private Button reviewEventButton;

    private ArrayList<String> eventNames;
    private ArrayList<String> eventDepartments;
    private ArrayList<String> eventLocations;
    private ArrayList<String> eventDates;
    private ArrayList<String> eventTimes;
    private ArrayList<String> eventDescriptions;
    private ArrayList<Integer> eventMaxLimits;

    int eventIndex;
    int eventsTotal;
    int nextChronologicalEventIndex = 2;
    int currentEventCapacity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // initialize view pointers now that layout has switched
        eventName = (TextView) findViewById(R.id.eventName);
        eventDepartment = (TextView) findViewById(R.id.eventDepartment);
        eventLocation = (TextView) findViewById(R.id.eventLocation);
        eventDateTime = (TextView) findViewById(R.id.eventDateTime);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        attendSwitch = (SwitchCompat) findViewById(R.id.attendSwitch);
        rsvpSwitch = (SwitchCompat) findViewById(R.id.rsvpSwitch);
        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);
        reviewRatingBar = (RatingBar) findViewById(R.id.reviewRatingBar);
        reviewEditText = (EditText) findViewById(R.id.reviewEditText);
        reviewEventButton = (Button) findViewById(R.id.reviewEventButton);
        backButton = (Button) findViewById(R.id.backButton);

        // this function gets event details from db and displays on page
        refreshEvents(true);

        // this section allows the switches to request/cancel rsvp/attending status
        attendSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEventIndex(eventIndex);
                if (attendSwitch.isChecked()) {
                    if (currentEventCapacity < eventMaxLimits.get(eventIndex)) {
                        eventsRef.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("review").setValue("");
                        Toast.makeText(EventsActivity.this, "You have secured a spot at " + eventNames.get(eventIndex) + ".", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EventsActivity.this, eventNames.get(eventIndex) + " is full, sorry!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    eventsRef.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).removeValue();
                    Toast.makeText(EventsActivity.this, "You are not going to " + eventNames.get(eventIndex) + ".", Toast.LENGTH_SHORT).show();
                }
                goToEventIndex(eventIndex);

            }
        });
        rsvpSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rsvpSwitch.isChecked()) {
                    eventsRef.child(eventNames.get(eventIndex)).child("rsvpList").child(currentUser).setValue(currentUser);
                    Toast.makeText(EventsActivity.this, "You will be notified of any updates to " + eventNames.get(eventIndex) + ".", Toast.LENGTH_SHORT).show();
                } else {
                    eventsRef.child(eventNames.get(eventIndex)).child("rsvpList").child(currentUser).removeValue();
                    Toast.makeText(EventsActivity.this, "You will not be notified of changes to " + eventNames.get(eventIndex) + ".", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // this section allows the left/right buttons to navigate through the events
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventIndex--;
                goToEventIndex(eventIndex);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventIndex++;
                goToEventIndex(eventIndex);
            }
        });

        // review event button listener
        reviewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send review to database
                eventsRef.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("rating").setValue((Float) reviewRatingBar.getRating());
                eventsRef.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("review").setValue(reviewEditText.getText().toString());
                Toast.makeText(EventsActivity.this, "Your review of " + eventNames.get(eventIndex) + " was submitted!", Toast.LENGTH_SHORT).show();
            }
        });

        // back button listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventsActivity.this, StudentActivity.class));
            }
        });

    }

    private void refreshEvents(Boolean returnToPresent) {
        db = FirebaseDatabase.getInstance("https://b07data-default-rtdb.firebaseio.com/");
        eventsRef = db.getReference().child("events");
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // Get details from snapshot
                    eventNames = collectEventDetails(snapshot, "name");
                    eventDepartments = collectEventDetails(snapshot, "department");
                    eventLocations = collectEventDetails(snapshot, "location");
                    eventDates = collectEventDetails(snapshot, "date");
                    eventTimes = collectEventDetails(snapshot, "time");
                    eventDescriptions = collectEventDetails(snapshot, "description");
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (DataSnapshot event : snapshot.getChildren()) {
                        temp.add(event.child("maxLimit").getValue(Integer.class));
                    }
                    eventMaxLimits = temp;
                    eventsTotal = eventNames.size();

                    if (returnToPresent) {
                        nextChronologicalEventIndex = 2; // will be set to nearest event once chronological sorting is finished
                        eventIndex = nextChronologicalEventIndex;
                        goToEventIndex(eventIndex);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // section to handle error (will look into this later)
                }
            }

        );

    }

    @NonNull
    private ArrayList<String> collectEventDetails(DataSnapshot events, String detail) {
        ArrayList<String> list = new ArrayList<String>();
        for (DataSnapshot event : events.getChildren()) {
            list.add(event.child(detail).getValue(String.class));
        }
        return list;
    }

    /* to be finished (chronological sorting, adding UI and code for submitting event review)
    private void sortChronological(ArrayList<String> timeValues, ArrayList<String>[] dependants) {
        // find chronological reordering
        int length = timeValues.size();
        int[] reordering = new int[length];
        for (int i=0; i<length; i++) {
            for (int j=0; j<length; j++) {
                if (hashTime(timeValues.get(j)) < hashTime(timeValues.get(i))) {
                    reordering[j] = i;
                    reordering[i] = j;
                    break;
                }
            }
        }

        // apply reordering to all dependants
        for (ArrayList<String> dependant : dependants) {
            reorderWRT(dependant, reordering);
        }
    }

    private int hashTime(String time) {
        // make a chronologically linear hashing function
        return time.hashCode();
    }

    private ArrayList<String> reorderWRT(ArrayList<String> unordered, @NonNull int[] ordering) {
        ArrayList<String> reordered = null;
        for (int i : ordering) {
            reordered.add(unordered.get(i));
        }
        return reordered;
    } */

    private void goToEventIndex(int eventIndex) {
        // set text of each textView
        eventName.setText(eventNames.get(eventIndex));
        eventDepartment.setText(eventDepartments.get(eventIndex));
        eventLocation.setText(eventLocations.get(eventIndex));
        eventDateTime.setText(eventDates.get(eventIndex) + " at " + eventTimes.get(eventIndex));
        eventDescription.setText(eventDescriptions.get(eventIndex));

        // set visibility of left and right buttons
        // complicated because i couldn't figure out how to put buttons side by side
        if (eventIndex == 0) {
            leftButton.setVisibility(View.GONE);
        } else {
            leftButton.setVisibility(View.VISIBLE);
        }
        if (eventIndex == eventsTotal - 1) {
            rightButton.setVisibility(View.GONE);
        } else {
            rightButton.setVisibility(View.VISIBLE);
        }

        // set visibility, state and text of the switches based off of database and date
        // set visibility, state and text of event review components and date
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (eventIndex >= nextChronologicalEventIndex) {
                    attendSwitch.setVisibility(View.VISIBLE);
                    rsvpSwitch.setVisibility(View.VISIBLE);
                    attendSwitch.setChecked(snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).getValue() != null);
                    rsvpSwitch.setChecked(snapshot.child(eventNames.get(eventIndex)).child("rsvpList").child(currentUser).getValue() != null);
                    currentEventCapacity = 0;
                    for (DataSnapshot child : snapshot.child(eventNames.get(eventIndex)).child("attendees").getChildren()) {
                        currentEventCapacity++;
                    }
                    attendSwitch.setText("I want to attend this event! (" + currentEventCapacity + "/" + eventMaxLimits.get(eventIndex) + ")");
                } else {
                    attendSwitch.setVisibility(View.GONE);
                    rsvpSwitch.setVisibility(View.GONE);
                }

                if (eventIndex < nextChronologicalEventIndex && snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).getValue() != null) {
                    reviewRatingBar.setVisibility(View.VISIBLE);
                    reviewEditText.setVisibility(View.VISIBLE);
                    reviewEventButton.setVisibility(View.VISIBLE);
                    if (snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("rating").getValue() == null) {
                        reviewRatingBar.setRating(0);
                    } else {
                        reviewRatingBar.setRating(snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("rating").getValue(Float.class));
                    }
                    if (snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("review").getValue() == "") {
                        reviewEditText.setHint("Write a review here!");
                    } else {
                        reviewEditText.setText(snapshot.child(eventNames.get(eventIndex)).child("attendees").child(currentUser).child("review").getValue(String.class));
                    }
                } else {
                    reviewRatingBar.setVisibility(View.GONE);
                    reviewEditText.setVisibility(View.GONE);
                    reviewEventButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}

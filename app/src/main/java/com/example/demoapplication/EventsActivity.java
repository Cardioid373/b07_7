package com.example.demoapplication;

import android.media.metrics.Event;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.demoapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class EventsActivity extends AppCompatActivity {

    FirebaseDatabase db;

    private TextView eventName;
    private TextView eventDepartment;
    private TextView eventLocation;
    private TextView eventDateTime;
    private TextView eventDescription;
    private SwitchCompat attendSwitch;
    private SwitchCompat rsvpSwitch;
    private Button leftButton;
    private Button rightButton;
    private Button rightNoLeftButton;

    static ArrayList<String> eventNames = new ArrayList<String>(Collections.singleton("@string/eventName"));
    static ArrayList<String> eventDepartments = new ArrayList<String>(Collections.singleton("@string/eventName"));
    static ArrayList<String> eventLocations = new ArrayList<String>(Collections.singleton("@string/eventName"));
    static ArrayList<String> eventDates = new ArrayList<String>(Collections.singleton("@string/eventName"));
    static ArrayList<String> eventTimes = new ArrayList<String>(Collections.singleton("@string/eventName"));
    static ArrayList<String> eventDescriptions = new ArrayList<String>(Collections.singleton("@string/eventName"));

    int eventIndex;
    int eventsTotal;

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
        rightNoLeftButton = (Button) findViewById(R.id.rightNoLeftButton);

        // this function gets event details from db and displays on page
        refreshEvents(true);

        // this section allows the switches to request/cancel rsvp/attending status
        attendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    /* this section will request to attend
                     *
                     * comments:
                     *  - show capacity of event on attendSwitch e.g. I want to attend this event! (Attendees: 34/90)
                     *  - database children that store the values in above example?
                     *  - store individual users/usernames and count the length for the first value?
                     *  - where/how exactly to store users/usernames?
                     *  - send user a notification confirming spot at the event?
                     *
                     * temporary toast msg below
                     */
                    Toast.makeText(EventsActivity.this, "requesting to attend", Toast.LENGTH_SHORT).show();
                } else {
                    /* this section will cancel attendance
                     *
                     * temporary toast msg below
                     */
                    Toast.makeText(EventsActivity.this, "cancelling attendance", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rsvpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    /* this section will request rsvp
                     *
                     * comments:
                     *  - related to story 4, look to discuss this soon
                     *
                     * temporary toast msg below
                     */
                    Toast.makeText(EventsActivity.this, "requesting rsvp", Toast.LENGTH_SHORT).show();
                } else {
                    /* this section will cancel rsvp
                     *
                     * temporary toast msg below
                     */
                    Toast.makeText(EventsActivity.this, "cancelling rsvp", Toast.LENGTH_SHORT).show();
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
        rightNoLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventIndex++;
                goToEventIndex(eventIndex);
            }
        });

    }

    private void refreshEvents(Boolean returnToPresent) {
        db = FirebaseDatabase.getInstance("https://b07data-default-rtdb.firebaseio.com/");
        DatabaseReference eventRef = db.getReference().child("events");
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        // Get details from snapshot
                                                        eventNames = collectEventDetails(snapshot, "name");
                                                        eventDepartments = collectEventDetails(snapshot, "department");
                                                        eventLocations = collectEventDetails(snapshot, "location");
                                                        eventDates = collectEventDetails(snapshot, "date");
                                                        eventTimes = collectEventDetails(snapshot, "time");
                                                        eventDescriptions = collectEventDetails(snapshot, "description");
                                                        eventsTotal = eventNames.size();
                                                        if (returnToPresent) {
                                                            eventIndex = 0; // will be set to nearest event if chronological sorting is finished
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
        for (DataSnapshot snapshot : events.getChildren()) {
            list.add(snapshot.child(detail).getValue(String.class));
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
            rightButton.setVisibility(View.GONE);
            if (eventIndex == eventsTotal - 1) {
                rightNoLeftButton.setVisibility(View.GONE);
            } else {
                rightNoLeftButton.setVisibility(View.VISIBLE);
            }
        } else {
            leftButton.setVisibility(View.VISIBLE);
            rightNoLeftButton.setVisibility(View.GONE);
            if (eventIndex == eventsTotal - 1) {
                rightButton.setVisibility(View.GONE);
            } else {
                rightButton.setVisibility(View.VISIBLE);
            }
        }

        // set state of the switches based off of student's choice
    }

}
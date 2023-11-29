package com.example.demoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventFeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference db;
    EventFeedbackAdapter eventFeedbackAdapter;
    ArrayList<EventFeedback> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_feedback);

        recyclerView = findViewById(R.id.Feedback);
        db = FirebaseDatabase.getInstance().getReference("events");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        eventFeedbackAdapter = new EventFeedbackAdapter(this, list);
        recyclerView.setAdapter(eventFeedbackAdapter);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String eventName = dataSnapshot.child("name").getValue(String.class);
                    EventFeedback eventFeedback = new EventFeedback(eventName);
                    int ratingNum = 0;
                    double ratingTotal = 0.0;
                    StringBuilder feedback = new StringBuilder();

                    DataSnapshot attendeesSnapshot = dataSnapshot.child("attendees");
                    for (DataSnapshot attendeeSnapshot : attendeesSnapshot.getChildren()) {
                        Double rating = attendeeSnapshot.child("rating").getValue(Double.class);
                        String review = attendeeSnapshot.child("review").getValue(String.class);

                        if (rating != null) {
                            ratingNum += 1;
                            ratingTotal += rating;
                        }

                        if (review != null && !review.isEmpty()) {
                            feedback.append(review).append("\n");
                        }
                    }

                    double ratingAvg = ratingNum > 0 ? ratingTotal / ratingNum : 0;

                    eventFeedback.setRatingNum(ratingNum);
                    eventFeedback.setRatingAvg(ratingAvg);
                    eventFeedback.setFeedback(feedback.toString());

                    list.add(eventFeedback);

                    dataSnapshot.getRef().child("numRatings").setValue(ratingNum);
                    dataSnapshot.getRef().child("averageRating").setValue(ratingAvg);
                }

                eventFeedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btnFeedbackBack = findViewById(R.id.EventFeedbackBack);

        btnFeedbackBack.setOnClickListener(view -> startActivity(new Intent(EventFeedbackActivity.this, AdminHomeActivity.class)));

    }
}
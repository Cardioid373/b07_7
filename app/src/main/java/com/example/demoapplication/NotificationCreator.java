package com.example.demoapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationCreator {

    private DatabaseReference notificationRef;
    private DatabaseReference eventRef;
    private DatabaseReference announcementRef;

    public NotificationCreator() {
        notificationRef = FirebaseDatabase.getInstance().getReference("notifications");
        eventRef = FirebaseDatabase.getInstance().getReference("events");
        announcementRef = FirebaseDatabase.getInstance().getReference("announcements");
    }

    public void createEventNotifications() {
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String eventName = eventSnapshot.child("name").getValue(String.class);
                    String eventDate = eventSnapshot.child("date").getValue(String.class);
                    String eventDepartment = eventSnapshot.child("department").getValue(String.class);
                    String eventDescription = eventSnapshot.child("description").getValue(String.class);
                    String eventLocation = eventSnapshot.child("location").getValue(String.class);
                    String eventTime = eventSnapshot.child("time").getValue(String.class);

                    String eventContent = "Date: " + eventDate + "\nTime: " + eventTime + "\nLocation: " + eventLocation + "\nDepartment: " + eventDepartment + "\nDescription: " + eventDescription;

                    // Push the new notification to the Notifications list
                    notificationRef.child(eventName).setValue(new Notification("event", eventName, eventContent));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("NotificationActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }

    public void createAnnouncementNotifications() {
        announcementRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot announcementSnapshot : dataSnapshot.getChildren()) {
                    String announcementTitle = announcementSnapshot.child("title").getValue(String.class);
                    String announcementContent = announcementSnapshot.child("content").getValue(String.class);

                    // Push the new notification to the Notifications list
                    notificationRef.child(announcementTitle).setValue(new Notification("announcement", announcementTitle, announcementContent));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("NotificationActivity", "Database error: " + databaseError.getMessage());
            }
        });
    }
}

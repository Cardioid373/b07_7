package com.example.demoapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference notificationRef;
    DatabaseReference eventRef;
    DatabaseReference announcementRef;
    NotificationAdapter notificationAdapter;
    ArrayList<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Instantiate the NotificationCreator
        NotificationCreator notificationCreator = new NotificationCreator();

        // Call the methods to create notifications
        notificationCreator.createEventNotifications();
        notificationCreator.createAnnouncementNotifications();

        notificationRef = FirebaseDatabase.getInstance().getReference("notifications");
        eventRef = FirebaseDatabase.getInstance().getReference("events");
        announcementRef = FirebaseDatabase.getInstance().getReference("announcements");

        recyclerView = findViewById(R.id.Notifications);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this,notificationList);
        recyclerView.setAdapter(notificationAdapter);

        eventRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                // Create a corresponding notification in the Notifications list
                String eventName = dataSnapshot.child("name").getValue(String.class);
                String eventDate = dataSnapshot.child("date").getValue(String.class);
                String eventDepartment = dataSnapshot.child("department").getValue(String.class);
                String eventDescription = dataSnapshot.child("description").getValue(String.class);
                String eventLocation = dataSnapshot.child("location").getValue(String.class);
                String eventTime = dataSnapshot.child("time").getValue(String.class);

                String eventContent = "Date: " + eventDate + "\nTime: " + eventTime + "\nLocation: " + eventLocation + "\nDepartment: " + eventDepartment + "\nDescription: " + eventDescription;

                // Push the new notification to the Notifications list
                notificationRef.child(eventName).setValue(new Notification("event", eventName, eventContent));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        announcementRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {
                // Create a corresponding notification in the Notifications list
                String announcementTitle = dataSnapshot.child("title").getValue(String.class);
                String announcementContent = dataSnapshot.child("content").getValue(String.class);

                // Push the new notification to the Notifications list
                notificationRef.child(announcementTitle).setValue(new Notification("announcement", announcementTitle, announcementContent));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        notificationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Notification notification = dataSnapshot.getValue(Notification.class);
                    notificationList.add(notification);
                }
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
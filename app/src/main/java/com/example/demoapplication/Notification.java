package com.example.demoapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Notification extends AppCompatActivity {

    private String type;
    private String title;
    private String content;

    public Notification(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    // Getters for type, title, and content
    public String getNotificationType() {
        return type;
    }
    public String getNotificationTitle() {
        return title;
    }
    public String getNotificationContent() {
        return content;
    }

    // Setters for type, title, and content
    public void setNotificationType(String type) {
        this.type = type;
    }
    public void setNotificationTitle(String title) {
        this.title = title;
    }
    public void setNotificationContent(String content) {
        this.content = content;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");
        Query query = databaseReference.orderByChild("type");
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = findViewById(R.id.Notifications);

        FirebaseRecyclerOptions<Notification> options =
                new FirebaseRecyclerOptions.Builder<Notification>()
                        .setQuery(query, Notification.class)
                        .build();

        FirebaseRecyclerAdapter<Notification, NotificationViewHolder> adapter =
                new FirebaseRecyclerAdapter<Notification, NotificationViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotificationViewHolder holder, int position, @NonNull Notification model) {
                        if (model.getNotificationType().equals("event")) {
                            holder.iconImageView.setImageResource(R.drawable.notification_event);
                        } else if (model.getNotificationType().equals("announcement")) {
                            holder.iconImageView.setImageResource(R.drawable.notification_announcement);
                        }
                        holder.titleTextView.setText(model.getNotificationTitle());
                        holder.contentTextView.setText(model.getNotificationContent());
                    }

                    @NonNull
                    @Override
                    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        // Inflate the layout for the RecyclerView item here
                        // ...
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
                        return new NotificationViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
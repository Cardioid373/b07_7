package com.example.demoapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    Context context;
    ArrayList<Notification> notificationList;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);
        if (notification != null && notification.getNotificationType() != null && notification.getNotificationContent() != null && notification.getNotificationTitle() != null) {
            if (notification.getNotificationType().equals("event")) {
                holder.iconImageView.setImageResource(R.drawable.notification_event);
            } else if (notification.getNotificationType().equals("announcement")) {
                holder.iconImageView.setImageResource(R.drawable.notification_announcement);
            }
            holder.titleTextView.setText(notification.getNotificationTitle());
            holder.contentTextView.setText(notification.getNotificationContent());
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{

        ImageView iconImageView;
        TextView titleTextView;
        TextView contentTextView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.NotificationIcon);
            titleTextView = itemView.findViewById(R.id.NotificationTitle);
            contentTextView = itemView.findViewById(R.id.NotificationContent);
        }
    }

}

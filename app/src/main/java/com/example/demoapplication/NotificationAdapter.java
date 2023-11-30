package com.example.demoapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final NotificationInterface notificationInterface;

    Context context;
    ArrayList<Notification> notificationList;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationList,
                               NotificationInterface notificationInterface) {
        this.context = context;
        this.notificationList = notificationList;
        this.notificationInterface = notificationInterface;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view, notificationInterface);
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

        if (notification.isRead()) {
            holder.iconImageView.setColorFilter(ContextCompat.getColor(context, R.color.black));
        } else {
            holder.iconImageView.setColorFilter(ContextCompat.getColor(context, R.color.purple_500));
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

        public NotificationViewHolder(@NonNull View itemView, NotificationInterface notificationInterface) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.NotificationIcon);
            titleTextView = itemView.findViewById(R.id.NotificationTitle);
            contentTextView = itemView.findViewById(R.id.NotificationContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notificationInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            notificationInterface.onItemClick(position);
                        }

                    }
                }
            });
        }
    }

}

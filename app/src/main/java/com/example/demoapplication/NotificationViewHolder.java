package com.example.demoapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
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

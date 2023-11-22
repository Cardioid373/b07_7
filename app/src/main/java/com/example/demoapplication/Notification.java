package com.example.demoapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notification extends FirebaseMessagingService {

    private static final String CHANNEL_ANNOUNCEMENTS = "announcements";
    private static final String CHANNEL_EVENTS = "events";

    @Override
    public void onNewToken(@NonNull String s) {
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getNotification() != null) {
            getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());
        }
    }

    private void getFirebaseMessage(String title, String body) {
        createNotificationChannel(CHANNEL_ANNOUNCEMENTS, "Announcement");
        createNotificationChannel(CHANNEL_EVENTS, "Event");

        Intent announcementIntent = new Intent(this, AnnouncementActivity.class);
        PendingIntent announcementPendingIntent = PendingIntent.getActivity(this, 0, announcementIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent eventIntent = new Intent(this, EventActivity.class);
        PendingIntent eventPendingIntent = PendingIntent.getActivity(this, 0, eventIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builderAnnouncement = new NotificationCompat.Builder(this, CHANNEL_ANNOUNCEMENTS)
                .setSmallIcon(R.drawable.ic_notification_announcement)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(announcementPendingIntent);

        NotificationCompat.Builder builderEvent = new NotificationCompat.Builder(this, CHANNEL_EVENTS)
                .setSmallIcon(R.drawable.ic_notification_event)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(eventPendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(100, builderAnnouncement.build());
        managerCompat.notify(101, builderEvent.build());
    }

    private void createNotificationChannel(String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
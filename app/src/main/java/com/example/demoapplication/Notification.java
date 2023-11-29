package com.example.demoapplication;

public class Notification {
    private String notificationType;
    private String notificationTitle;
    private String notificationContent;

    public Notification(){

    }

    public Notification(String notificationType, String notificationTitle, String notificationContent) {
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }
}
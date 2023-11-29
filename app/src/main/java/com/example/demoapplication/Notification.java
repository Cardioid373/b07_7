package com.example.demoapplication;

public class Notification {
    private String notificationType;
    private String notificationTitle;
    private String notificationContent;
    private Boolean isRead;

    public Notification(){
        this.isRead = false;
    }

    public Notification(String notificationType, String notificationTitle, String notificationContent) {
        this.notificationType = notificationType;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.isRead = false;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
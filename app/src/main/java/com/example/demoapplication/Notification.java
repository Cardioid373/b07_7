package com.example.demoapplication;

public class Notification {
    private String type;
    private String title;
    private String content;

    public Notification(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public String getNotificationType() {
        return type;
    }

    public String getNotificationTitle() {
        return title;
    }

    public String getNotificationContent() {
        return content;
    }
}

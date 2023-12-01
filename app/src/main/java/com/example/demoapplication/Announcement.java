package com.example.demoapplication;

public class Announcement {
    private String title;
    private String date;
    private String time;
    private String body;
    private String author;

    private String timeCreated;

    public Announcement(String title, String date, String time, String body, String author, String timeCreated) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.body = body;
        this.author = author;
        this.timeCreated = timeCreated;
    }



    public String getTitle() { return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}
    public String getBody() {return body;}
    public void setBody(String body) {this.body = body;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getTimeCreated() {
        return timeCreated;
    }
}

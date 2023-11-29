package com.example.demoapplication;

public class EventFeedback {
    String eventName;
    int ratingNum;
    double ratingAvg;
    String feedback;

    public EventFeedback(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

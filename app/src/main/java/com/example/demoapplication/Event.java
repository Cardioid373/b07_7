package com.example.demoapplication;

public class Announcement {
    private int maxLimit;
    private String date;
    private String time;
    private String location;
    private String name;
    private String description;
    private float averageRating;
    private int numRatings;
    private String department;

    public Announcement() {
        // Default constructor required for calls to DataSnapshot.getValue(Announcement.class)
    }

    public Announcement(String name, String data, String time, String location, String department, String description, int maxLimit, float averageRating, int numRatings) {
        this.maxLimit = maxLimit;
        this.date = date;
        this.time = time;
        this.location = location;
        this.name = name;
        this.description = description;
        this.averageRating = averageRating;
        this.numRatings = numRatings;
        this.department = department;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public String getDepartment() {
        return department;
    }
}
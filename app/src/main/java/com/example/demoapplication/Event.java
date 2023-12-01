package com.example.demoapplication;

public class Event {
    private int maxLimit;
    private String date;
    private String time;
    private String location;
    private String name;
    private String description;
    private float averageRating;
    private int numRatings;
    private String department;

    private String timeCreated;

    public Event() {
    }

    public Event(String name, String date, String time, String location, String department, String description, int maxLimit, float averageRating, int numRatings, String timeCreated) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.department = department;
        this.description = description;
        this.maxLimit = maxLimit;
        this.averageRating = averageRating;
        this.numRatings = numRatings;
        this.timeCreated = timeCreated;
    }



    public String getName() {
        return name;
    }

    public String getTimeCreated() {
        return timeCreated;
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

    public String getDepartment() {
        return department;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getNumRatings() {
        return numRatings;
    }


}

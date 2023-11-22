package com.example.demoapplication;

public class Complaints {
    private String complaintText;

    // Required default constructor for Firebase
    public Complaints() {
    }

    public Complaints(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getComplaintText() {
        return this.complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }
}

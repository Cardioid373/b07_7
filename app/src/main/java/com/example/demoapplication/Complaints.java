package com.example.demoapplication;

public class Complaints {
    private String complaintText;
    private String complaintName;
    private long index;

    public Complaints() {
    }

    public Complaints(long index, String complaintName, String complaintText) {
        this.index = index;
        this.complaintName = complaintName;
        this.complaintText = complaintText;
    }

    public String getComplaintText() {
        return this.complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getComplaintName() {
        return this.complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }
}
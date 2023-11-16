package com.example.demoapplication;

public class User {
    private String password; // You can add other user fields as needed

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
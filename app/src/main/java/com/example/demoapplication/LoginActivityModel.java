package com.example.demoapplication;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityModel {

    private final DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference adminDatabaseReference = FirebaseDatabase.getInstance().getReference("admins");

    public void checkUserPassword(LoginActivityPresenter presenter, String name, String password) {
        userDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(name).exists()) {
                    String storedPassword = dataSnapshot.child(name).child("password").getValue(String.class);
                    if (password.equals(storedPassword)) {
                        presenter.loginUser();
                    }else{
                        presenter.loginError("Incorrect user or password");
                    }
                } else {
                    presenter.loginError("Incorrect user or password");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoginActivity", "Database Error: " + databaseError.getMessage());
                presenter.loginError("Database Error: " + databaseError.getMessage());
            }
        });
    }

    public void checkAdminPassword(LoginActivityPresenter presenter, String name, String password) {
        adminDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(name).exists()) {
                    String storedPassword = dataSnapshot.child(name).child("password").getValue(String.class);
                    if (password.equals(storedPassword)) {
                        presenter.loginAdmin();
                    }else{
                        presenter.loginError("Incorrect user or password");
                    }
                } else {
                    presenter.loginError("Incorrect user or password");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoginActivity", "Database Error: " + databaseError.getMessage());
                presenter.loginError("Database Error: " + databaseError.getMessage());
            }
        });
    }
}

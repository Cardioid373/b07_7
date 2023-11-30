package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {
    private DatabaseReference adminDatabaseReference;

    static String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        adminDatabaseReference = FirebaseDatabase.getInstance().getReference("admins");

        EditText adminNameEditText = findViewById(R.id.adminNameEditText);
        EditText adminPasswordEditText = findViewById(R.id.adminPasswordEditText);
        Button adminLoginButton = findViewById(R.id.adminLoginButton);

        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = adminNameEditText.getText().toString();
                String password = adminPasswordEditText.getText().toString();
                currentUser = adminNameEditText.getText().toString();
                checkAdmin(name, password);
            }
        });

        Button btnGoBack = findViewById(R.id.btnGoBack);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void checkAdmin(final String name, final String password) {
        adminDatabaseReference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Admin admin = dataSnapshot.getValue(Admin.class);
                    if (admin != null && password.equals(admin.getPassword())) {
                        Toast.makeText(AdminLoginActivity.this, "Admin login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
                    } else {
                        Toast.makeText(AdminLoginActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminLoginActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminLoginActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
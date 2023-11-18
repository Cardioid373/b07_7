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

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        EditText getEmail = findViewById(R.id.emailEditText);
        EditText getPassword = findViewById(R.id.passwordEditText);
        Button btnRegister = findViewById(R.id.loginButton);
        Button btnBackToLogin = findViewById(R.id.goBackButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserExists(getEmail.getText().toString(), getPassword.getText().toString());
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();  // Close the current activity
            }
        });
    }

    private void checkUserExists(final String email, final String password) {
        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "User with the same email already exists", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisterActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser(final String email, final String password) {

        User newUser = new User(password);
        databaseReference.child(email).setValue(newUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(RegisterActivity.this, "Registration successful! Please log in now.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
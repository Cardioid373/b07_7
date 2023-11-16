package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.BlankActivity;
import com.example.demoapplication.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        User user1 = new User("hi");

// Add user1 data to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child("user1").setValue(user1);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseDatabase db = FirebaseDatabase.getInstance("https://b07data-default-rtdb.firebaseio.com/");
        EditText getEmail = findViewById(R.id.emailEditText);
        EditText getPassword = findViewById(R.id.passwordEditText);
        Button btnLogin = findViewById(R.id.loginButton);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve entered email and password
                String email = getEmail.getText().toString();
                String password = getPassword.getText().toString();

                // Check if the user exists in the database
                checkUser(email, password);
            }
        });
    }

    private void checkUser(final String email, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(email).exists()) {
                    String storedPassword = dataSnapshot.child(email).child("password").getValue(String.class);

                    if (password.equals(storedPassword)) {
                        // Login successful
                        Log.d("LoginActivity", "Login successful");
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, BlankActivity.class));
                    } else {
                        // Incorrect password
                        Log.d("LoginActivity", "Incorrect password");
                        Toast.makeText(MainActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User does not exist
                    Log.d("LoginActivity", "User does not exist");
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("LoginActivity", "Database Error: " + databaseError.getMessage());
                Toast.makeText(MainActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
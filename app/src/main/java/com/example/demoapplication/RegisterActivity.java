package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapplication.MainActivity;
import com.example.demoapplication.R;
import com.example.demoapplication.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReferenceUser;
    private DatabaseReference databaseReferenceAdmin;
    private Switch switchUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users");
        databaseReferenceAdmin = FirebaseDatabase.getInstance().getReference("admins");
        EditText getEmail = findViewById(R.id.emailEditText);
        EditText getPassword = findViewById(R.id.passwordEditText);
        Button btnRegister = findViewById(R.id.loginButton);
        Button btnBackToLogin = findViewById(R.id.goBackButton);
        switchUserType = findViewById(R.id.switchUserType);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdmin = switchUserType.isChecked();
                if (isAdmin == false) {
                    checkUserExists(getEmail.getText().toString(), getPassword.getText().toString());
                } else {
                    checkAdminExists(getEmail.getText().toString(), getPassword.getText().toString());
                }
            }
        });

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

        switchUserType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }

    private void checkUserExists(final String email, final String password) {
        databaseReferenceUser.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void checkAdminExists(final String email, final String password) {
        databaseReferenceAdmin.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "User with the same email already exists", Toast.LENGTH_SHORT).show();
                } else {
                    registerAdmin(email, password);
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
        databaseReferenceUser.child(email).setValue(newUser, new DatabaseReference.CompletionListener() {
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

    private void registerAdmin(final String email, final String password) {
        Admin newAdmin = new Admin(password);
        databaseReferenceAdmin.child(email).setValue(newAdmin, new DatabaseReference.CompletionListener() {
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
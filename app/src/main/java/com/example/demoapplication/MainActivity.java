package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    static String currentUser;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private final DatabaseReference adminDatabaseReference = FirebaseDatabase.getInstance().getReference("admins");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnGoToRegister = findViewById(R.id.btnGoToRegister);
        EditText getName = findViewById(R.id.nameEditText);
        EditText getPassword = findViewById(R.id.passwordEditText);
        Button btnLogin = findViewById(R.id.loginButton);
        Switch isAdminSwitch = findViewById(R.id.switch1);
        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        //Button btnAdminLogin = findViewById(R.id.btnAdminLogin);
        //btnAdminLogin.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
        //    }
        //});

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdmin = isAdminSwitch.isChecked();
                currentUser = getName.getText().toString();
                if (isAdmin == false) {
                    checkUser(getName.getText().toString(), getPassword.getText().toString());
                } else {
                    checkAdmin(getName.getText().toString(), getPassword.getText().toString());
                }

            }
        });


    }

    private void checkUser(final String name, final String password) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(name).exists()) {
                    String storedPassword = dataSnapshot.child(name).child("password").getValue(String.class);
                    if (password.equals(storedPassword)) {
                        currentUser = name;
                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, StudentActivity.class));

                    }else{
                        Toast.makeText(MainActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LoginActivity", "Database Error: " + databaseError.getMessage());
                Toast.makeText(MainActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                        currentUser = name;
                        Toast.makeText(MainActivity.this, "Admin login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));

                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect user or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}

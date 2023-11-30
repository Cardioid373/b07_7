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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivityView extends AppCompatActivity {

    static String currentUser;
    private final DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference("users");
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
        LoginActivityPresenter presenter = new LoginActivityPresenter(this, new LoginActivityModel());

        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivityView.this, RegisterActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdmin = isAdminSwitch.isChecked();
                currentUser = getName.getText().toString();
                if (isAdmin == false) {
                    presenter.checkUser(getName.getText().toString(), getPassword.getText().toString());
                } else {
                    presenter.checkAdmin(getName.getText().toString(), getPassword.getText().toString());
                }
            }
        });
    }

    public void startStudentActivity() {
        startActivity(new Intent(LoginActivityView.this, StudentActivity.class));
        finish();
    }

    public void startAdminActivity() {
        startActivity(new Intent(LoginActivityView.this, AdminHomeActivity.class));
        finish();
    }

    public void toast(String message) {
        Toast.makeText(LoginActivityView.this, message, Toast.LENGTH_SHORT).show();
    }
}

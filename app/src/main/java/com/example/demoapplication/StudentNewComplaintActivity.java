package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentNewComplaintActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        FirebaseApp.initializeApp(this);


        EditText getNewComplaintSubject = findViewById(R.id.newComplaintSubjectText);
        EditText getNewComplaint = findViewById(R.id.newComplaintText);
        Button btnNewComplaintSubmit = findViewById(R.id.newComplaintSubmitButton);
        Button btnNewComplaintBack = findViewById(R.id.newComplaintBackButton);

        btnNewComplaintSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Submit the complaint to firebase
                String complaint = getNewComplaint.getText().toString();
                String subject = getNewComplaintSubject.getText().toString();
                Complaints newComplaint = new Complaints(complaint);
                databaseReference.child(subject).setValue(newComplaint);
                Toast.makeText(StudentNewComplaintActivity.this, "Submission Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StudentNewComplaintActivity.this, StudentComplaintsActivity.class));
            }
        });

        btnNewComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to complaints page
                startActivity(new Intent(StudentNewComplaintActivity.this, StudentComplaintsActivity.class));
            }
        });
    }
}

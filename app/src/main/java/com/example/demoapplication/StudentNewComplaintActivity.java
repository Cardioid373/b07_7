package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentNewComplaintActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");


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
                databaseReference.child(subject).setValue(newComplaint, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                        if(error == null) {
                            Toast.makeText(StudentNewComplaintActivity.this, "Submission Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StudentNewComplaintActivity.this, StudentComplaintsActivity.class));
                            finish();
                        } else {
                            Toast.makeText(StudentNewComplaintActivity.this, "Submission Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnNewComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return to complaints page
                startActivity(new Intent(StudentNewComplaintActivity.this, StudentComplaintsActivity.class));
                finish();
            }
        });
    }
}

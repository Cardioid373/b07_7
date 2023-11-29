package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class StudentNewComplaintActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private DatabaseReference totalComplaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");
        totalComplaintsRef = FirebaseDatabase.getInstance().getReference().child("TotalComplaints");
        EditText getNewComplaintSubject = findViewById(R.id.newComplaintSubjectText);
        EditText getNewComplaint = findViewById(R.id.newComplaintText);
        Button btnNewComplaintSubmit = findViewById(R.id.newComplaintSubmitButton);
        Button btnNewComplaintBack = findViewById(R.id.newComplaintBackButton);

        btnNewComplaintSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitComplaint(getNewComplaintSubject, getNewComplaint);
            }
        });

        btnNewComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentNewComplaintActivity.this, StudentActivity.class));
                finish();
            }
        });
    }

    private void submitComplaint(EditText subjectEditText, EditText complaintEditText) {
        String subject = subjectEditText.getText().toString().trim();
        String complaintText = complaintEditText.getText().toString().trim();
        if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(complaintText)) {
            Toast.makeText(StudentNewComplaintActivity.this, "Subject and complaint cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        totalComplaintsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    long currentIndex = (long) snapshot.getValue();
                    totalComplaintsRef.setValue(currentIndex + 1);
                    Complaints newComplaint = new Complaints(currentIndex + 1, subject, complaintText);
                    databaseReference.child(String.valueOf(currentIndex + 1)).setValue(newComplaint, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError error, DatabaseReference ref) {
                            if (error == null) {
                                Toast.makeText(StudentNewComplaintActivity.this, "Submission Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(StudentNewComplaintActivity.this, StudentActivity.class));
                                finish();
                            } else {
                                Toast.makeText(StudentNewComplaintActivity.this, "Submission Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("StudentNewComplaintAct", "Database Error: " + error.getMessage());
                Toast.makeText(StudentNewComplaintActivity.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
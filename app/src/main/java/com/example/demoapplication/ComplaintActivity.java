package com.example.demoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapplication.Complaints;
import com.example.demoapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComplaintActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ComplaintAdapter complaintsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ArrayList<Complaints> complaintsList = new ArrayList<>();
        recyclerView = findViewById(R.id.complaintRecyclerView);
        complaintsAdapter = new ComplaintAdapter(complaintsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(complaintsAdapter);

        retrieveComplaints();

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ComplaintActivity.this, AdminHomeActivity.class));
                finish();
            }
        });
    }

    private void retrieveComplaints() {
        DatabaseReference complaintsRef = FirebaseDatabase.getInstance().getReference().child("complaints");
        complaintsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintsAdapter.clearComplaints();
                for (DataSnapshot complaintSnapshot : dataSnapshot.getChildren()) {
                    Complaints complaint = complaintSnapshot.getValue(Complaints.class);
                    complaintsAdapter.addComplaint(complaint);
                }
                complaintsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ComplaintActivity.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
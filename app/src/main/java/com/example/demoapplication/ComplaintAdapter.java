package com.example.demoapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private ArrayList<Complaints> complaintsList;

    public ComplaintAdapter(ArrayList<Complaints> complaintsList) {
        this.complaintsList = complaintsList;
        sortComplaints();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaints complaint = complaintsList.get(position);
        holder.complaintText.setText(complaint.getComplaintText());
        holder.complaintName.setText(complaint.getComplaintName());
    }

    @Override
    public int getItemCount() {
        return complaintsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView complaintText;
        TextView complaintName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            complaintText = itemView.findViewById(R.id.complaintText);
            complaintName = itemView.findViewById(R.id.complaintName);
        }
    }

    public void addComplaint(Complaints complaint) {
        complaintsList.add(complaint);
        sortComplaints();
        notifyDataSetChanged();
    }

    public void clearComplaints() {
        complaintsList.clear();
        notifyDataSetChanged();
    }

    private void sortComplaints() {
        Collections.sort(complaintsList, new Comparator<Complaints>() {
            @Override
            public int compare(Complaints complaint1, Complaints complaint2) {
                return Long.compare(complaint2.getIndex(), complaint1.getIndex());
            }
        });
    }
}
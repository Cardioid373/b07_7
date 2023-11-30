package com.example.demoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventFeedbackAdapter extends RecyclerView.Adapter<EventFeedbackAdapter.EventFeedbackViewHolder> {

    Context context;
    ArrayList<EventFeedback> list;

    public EventFeedbackAdapter(Context context, ArrayList<EventFeedback> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventFeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feedback_item, parent, false);
        return new EventFeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventFeedbackViewHolder holder, int position) {
        EventFeedback eventFeedback = list.get(position);
        holder.eventName.setText(eventFeedback.getEventName());
        holder.ratingNum.setText(String.valueOf(eventFeedback.getRatingNum()));
        holder.ratingAvg.setText(String.valueOf(eventFeedback.getRatingAvg()));
        holder.feedback.setText(eventFeedback.getFeedback());


        TextView ratingNumTextView = holder.itemView.findViewById(R.id.FeedbackRatingNum);
        TextView ratingAvgTextView = holder.itemView.findViewById(R.id.FeedbackRatingAvg);
        TextView feedbackTextView = holder.itemView.findViewById(R.id.FeedbackContent);

        if (eventFeedback.getRatingNum() == 0) {
            ratingNumTextView.setText(R.string.no_rating_message);
            ratingAvgTextView.setText("");
        } else {
            ratingNumTextView.setText(String.valueOf(eventFeedback.getRatingNum()));
            ratingAvgTextView.setText(String.valueOf(eventFeedback.getRatingAvg()));
        }

        if (eventFeedback.getFeedback() == null || eventFeedback.getFeedback().isEmpty()) {
            feedbackTextView.setText(R.string.no_feedback_message);
        } else {
            feedbackTextView.setText(eventFeedback.getFeedback());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EventFeedbackViewHolder extends RecyclerView.ViewHolder{

        TextView eventName;
        TextView ratingNum;
        TextView ratingAvg;
        TextView feedback;

        public EventFeedbackViewHolder(@NonNull View itemView) {
            super(itemView);

            eventName = itemView.findViewById(R.id.FeedbackEventName);
            ratingNum = itemView.findViewById(R.id.FeedbackRatingNum);
            ratingAvg = itemView.findViewById(R.id.FeedbackRatingAvg);
            feedback = itemView.findViewById(R.id.FeedbackContent);
        }
    }

}

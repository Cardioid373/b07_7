package com.example.demoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class course_check extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_check, container, false);

        // Retrieve information from arguments
        String questionText = getArguments().getString("questionText", "");
        String buttonClicked = getArguments().getString("buttonClicked", "");
        boolean answer = getArguments().getBoolean("answer", false);

        TextView questionTextView = view.findViewById(R.id.quest);
        questionTextView.setText(R.string.MathMajorAdmittedReq1);

        /* Handle Yes or No Answers
        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "Yes" button click based on buttonClicked and answer
                handleYesButtonClick(buttonClicked, answer);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle "No" button click based on buttonClicked and answer
                handleNoButtonClick(buttonClicked, answer);
            }
        });*/

        return view;
    }
    /*private void handleYesButtonClick(String buttonClicked, boolean answer) {

    }

    private void handleNoButtonClick(String buttonClicked, boolean answer) {

    }*/

}

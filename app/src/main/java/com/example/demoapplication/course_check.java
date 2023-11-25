package com.example.demoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class course_check extends Fragment {
    /*This fragment will present the user with questions regarding the course and
    course grade requirements of their selected program*/
    protected boolean answer1 = false;
    protected boolean answer2 = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_check, container, false);

        // Retrieve information from arguments
        String programChosen = getArguments().getString("programChosen", ""); // program the user chose
        boolean admitted = getArguments().getBoolean("answer", false); // whether they hit yes or no for admission

        // Buttons and Textviews
        TextView firstQuestion = view.findViewById(R.id.quest);
        TextView secondQuestion = view.findViewById(R.id.quest2);
        Button yesButton = view.findViewById(R.id.yesOne);
        Button noButton = view.findViewById(R.id.noOne);
        Button yesButton2 = view.findViewById(R.id.yesTwo);
        Button noButton2 = view.findViewById(R.id.noTwo);
        Button enterButton = view.findViewById(R.id.postEnterButton);

        // HANDLE WHICH QUESTIONS ARE DISPLAYED FOR CHOSEN PROGRAM
        if(programChosen.equals("mathMajor")){
            if(admitted) {
                firstQuestion.setText(R.string.MathMajorAdmittedReq1);
                secondQuestion.setText(R.string.MathMajorAdmittedReq2);
            }
            else {
                firstQuestion.setText(R.string.MathNotAdmittedRec1);
                hideQuestion(secondQuestion, yesButton2, noButton2);
            }
        }
        else if(programChosen.equals("mathSpecialist")) {
            if(admitted) {
                firstQuestion.setText(R.string.MathSpecialistAdmittedRec1);
                secondQuestion.setText(R.string.MathSpecialistAdmittedReq2);
            }
            else {
                firstQuestion.setText(R.string.MathNotAdmittedRec1);
                hideQuestion(secondQuestion, yesButton2, noButton2);
            }
        }
        else if(programChosen.equals("statsMajor")) {
            if(admitted) {
                firstQuestion.setText(R.string.StatsMajorAdmittedReq1);
            }
            else {
                firstQuestion.setText(R.string.StatsMajorNotAdmittedReq1);
            }
            hideQuestion(secondQuestion, yesButton2, noButton2);
        }
        else if(programChosen.equals("statsSpecialist")) {
            if(admitted) {
                firstQuestion.setText(R.string.StatsSpecialistAdmittedReq1);
            }
            else {
                firstQuestion.setText(R.string.StatsSpecialistNotAdmittedReq1);
            }
            hideQuestion(secondQuestion, yesButton2, noButton2);
        }
        else if(programChosen.equals("csMajor")) {
            firstQuestion.setText(R.string.CSMajorReq1);
            secondQuestion.setText(R.string.CSMajorReq2);
        }
        else if(programChosen.equals("csMinor")) {
            firstQuestion.setText(R.string.CSMinorReq1);
            hideQuestion(secondQuestion, yesButton2, noButton2);
        }

        // HANDLE YES, NO AND ENTER BUTTONS TO QUESTIONS
        yesButton.setOnClickListener(v -> answer1 = true);
        noButton.setOnClickListener(v -> answer1 = false);
        yesButton2.setOnClickListener(v -> answer2 = true);
        noButton2.setOnClickListener(v -> answer2 = false);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answer1 || !answer2) {
                    firstQuestion.setText(R.string.postReqNotMet);
                }
                else {
                    firstQuestion.setText(R.string.postReqMet);
                }

                // hide everything except the message
                hideQuestion(secondQuestion, yesButton2, noButton2);
                enterButton.setVisibility(View.GONE);
                yesButton.setVisibility(View.GONE);
                noButton.setVisibility(View.GONE);
            }
        });

        return view;
    }


    // This hides the buttons for the second question if there's only one POST requirement
    private void hideQuestion(TextView secondQuestion, Button yesButton2, Button noButton2) {
        secondQuestion.setVisibility(View.GONE);
        yesButton2.setVisibility(View.GONE);
        noButton2.setVisibility(View.GONE);
        answer2 = true;
    }


}

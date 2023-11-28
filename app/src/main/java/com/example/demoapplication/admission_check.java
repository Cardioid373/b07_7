package com.example.demoapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class admission_check extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admission_check, container, false);

        // retrieve arguments from PostActivity
        String questionText = getArguments().getString("questionText", "");
        String programChosen = getArguments().getString("programChosen", "");

        TextView admissionCheck = view.findViewById(R.id.admissionCheck);
        admissionCheck.setText(questionText);

        // Handle Yes or No Answers
        Button yesButton = view.findViewById(R.id.yesButton);
        Button noButton = view.findViewById(R.id.noButton);

        yesButton.setOnClickListener(v -> navigateToNextFragment(programChosen, true));
        noButton.setOnClickListener(v -> navigateToNextFragment(programChosen, false));
        return view;
    }

    private void navigateToNextFragment(String programChosen, boolean answer) {
        // the next fragment we will navigate to is the course_check fragment
        course_check nextFragment = new course_check();

        // Pass information to the course_check fragment
        Bundle bundle = new Bundle();
        bundle.putString("programChosen", programChosen);
        bundle.putBoolean("answer", answer);
        nextFragment.setArguments(bundle);

        // Navigate to the course_check fragment
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.questions_container, nextFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
package com.example.demoapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button csMajorButton = findViewById(R.id.CSMajorButton);
        Button csMinorButton = findViewById(R.id.CSMinorButton);
        Button mathMajorButton = findViewById(R.id.MathMajorButton);
        Button mathSpecialistButton = findViewById(R.id.MathSpecialistButton);
        Button statsMajorButton = findViewById(R.id.StatsMajorButton);
        Button statsSpecialistButton = findViewById(R.id.StatsSpecialistButton);
        Button postBackButton = findViewById(R.id.postBackButton);

        csMajorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.CSq1), "csMajor");
            }
        });

        csMinorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.CSq1), "csMinor");
            }
        });

        mathMajorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.Mathq1), "mathMajor");
            }
        });

        mathSpecialistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.Mathq1), "mathSpecialist");
            }
        });

        statsMajorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.Statsq1), "statsMajor");
            }
        });

        statsSpecialistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToFragment(new admission_check(), getString(R.string.Statsq1), "statsSpecialist");
            }
        });

        postBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void navigateToFragment(Fragment fragment, String questionText, String programChosen) {

        // pass info to admission_check fragment
        Bundle bundle = new Bundle();
        bundle.putString("questionText", questionText);
        bundle.putString("programChosen",programChosen );
        fragment.setArguments(bundle);

        // navigate to admission_check fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.questions_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

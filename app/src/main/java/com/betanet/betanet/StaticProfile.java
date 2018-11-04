package com.betanet.betanet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StaticProfile extends AppCompatActivity {
    ImageView profileImage;
    TextView nameTextView;
    TextView emailTextView;
    StaticElementList skillsStaticElementList, interestsStaticElementList, professionStaticElementList, institutionStaticElementList, achievementsStaticElementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_bio);
        profileImage = findViewById(R.id.profileImage);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        skillsStaticElementList = findViewById(R.id.skillsStaticElementList);
        interestsStaticElementList = findViewById(R.id.interestsStaticElementList);
        professionStaticElementList = findViewById(R.id.professionStaticElementList);
        institutionStaticElementList = findViewById(R.id.institutionStaticElementList);
        achievementsStaticElementList = findViewById(R.id.achievementsStaticElementList);

        String user_name = getIntent().getStringExtra("user_name");
        String email = getIntent().getStringExtra("email");

        // TODO get all 5
        skillsStaticElementList.setElementName("Skills");
        interestsStaticElementList.setElementName("Interests");
        professionStaticElementList.setElementName("Profession");
        institutionStaticElementList.setElementName("Institution");
        achievementsStaticElementList.setElementName("Achievements");
        nameTextView.setText(user_name);
        emailTextView.setText(email);
        // TODO set Image
    }

    public void onClickBackFromEditableProfile(View v) {
        onBackPressed();
    }
}
package com.betanet.betanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditableProfile extends AppCompatActivity {
    ImageView profileImage;
    ImageButton addImageLayout;
    TextView nameTextView;
    TextView emailTextView;
    EditableElementList skillsEditableElementList, interestsEditableElementList, professionEditableElementList, institutionEditableElementList, achievementsEditableElementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_bio);
        profileImage = findViewById(R.id.profileImage);
        addImageLayout = findViewById(R.id.addImageLayout);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        skillsEditableElementList = findViewById(R.id.skillsEditableElementList);
        interestsEditableElementList = findViewById(R.id.interestsEditableElementList);
        professionEditableElementList = findViewById(R.id.professionEditableElementList);
        institutionEditableElementList = findViewById(R.id.institutionEditableElementList);
        achievementsEditableElementList = findViewById(R.id.achievementsEditableElementList);

        String user_name = getIntent().getStringExtra("user_name");
        String email = getIntent().getStringExtra("email");

        // TODO get all 5 from db
        skillsEditableElementList.setElementName("Skills");
        interestsEditableElementList.setElementName("Interests");
        professionEditableElementList.setElementName("Profession");
        institutionEditableElementList.setElementName("Institution");
        achievementsEditableElementList.setElementName("Achievements");

        skillsEditableElementList.addElementTextView.setOnClickListener(v -> {
            Intent intent = new Intent(EditableProfile.this, AddSkills.class);
            startActivity(intent);
        });
        interestsEditableElementList.addElementTextView.setOnClickListener(v -> {
            Intent intent = new Intent(EditableProfile.this, AddInterests.class);
            startActivity(intent);
        });
        professionEditableElementList.addElementTextView.setOnClickListener(v -> {
            Intent intent = new Intent(EditableProfile.this, AddProfession.class);
            startActivity(intent);
        });
        institutionEditableElementList.addElementTextView.setOnClickListener(v -> {
            Intent intent = new Intent(EditableProfile.this, AddInstitution.class);
            startActivity(intent);
        });
        achievementsEditableElementList.addElementTextView.setOnClickListener(v -> {
            Intent intent = new Intent(EditableProfile.this, AddAchievements.class);
            startActivity(intent);
        });

        nameTextView.setText(user_name);
        emailTextView.setText(email);
        addImageLayout.setOnClickListener(view -> Toast.makeText(EditableProfile.this, "Add Image", Toast.LENGTH_SHORT).show());

        // TODO Set Image
    }

    public void onClickBackFromEditableProfile (View v) {
        onBackPressed();
    }
}
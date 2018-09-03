package com.betanet.betanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuickBio extends AppCompatActivity {
    ImageView profileImage;
    ImageButton addImageLayout;
    TextView nameTextView;
    TextView emailTextView;
    TextView addInterestsTextView;
    TextView addSkillsTextView;
    Button saveBioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_bio);
        profileImage = findViewById(R.id.profileImageLayout).findViewById(R.id.profileImage);
        addImageLayout = findViewById(R.id.profileImageLayout).findViewById(R.id.addImageLayout);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        addSkillsTextView = findViewById(R.id.addSkillsTextView);
        addInterestsTextView = findViewById(R.id.addInterestsTextView);
        saveBioButton = findViewById(R.id.saveBioButton);

        addImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuickBio.this, "Add Image", Toast.LENGTH_SHORT).show();
            }
        });
        addSkillsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickBio.this, AddSkills.class);
                startActivity(intent);
            }
        });
        addInterestsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuickBio.this, AddInterests.class);
                startActivity(intent);
            }
        });
        saveBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuickBio.this, "Save Bio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

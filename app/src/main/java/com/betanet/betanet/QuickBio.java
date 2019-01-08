package com.betanet.betanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuickBio extends AppCompatActivity {
    private static final String TAG = "QuickBio";
    ImageView profileImage;
    ImageButton addImageLayout;
    TextView nameTextView;
    TextView emailTextView;
    Button saveBioButton;
    EditableElementList skillsEditableElementList, interestsEditableElementList;

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
        saveBioButton = findViewById(R.id.saveBioButton);
        skillsEditableElementList.setElementName("Skills");
        interestsEditableElementList.setElementName("Interests");
        // TODO get skills and interests

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = preferences.getString("user_name", null);
        String email = preferences.getString("email_id", null);

        nameTextView.setText(user_name);
        emailTextView.setText(email);
        addImageLayout.setOnClickListener(view -> Toast.makeText(QuickBio.this, "Add Image", Toast.LENGTH_SHORT).show());


        skillsEditableElementList.addElementTextView.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = new Intent(QuickBio.this, AddSkills.class);
            startActivity(intent);
        });

        interestsEditableElementList.addElementTextView.setOnClickListener((View.OnClickListener) view -> {
            Intent intent = new Intent(QuickBio.this, AddInterests.class);
            startActivity(intent);
        });

        saveBioButton.setOnClickListener(view -> Toast.makeText(QuickBio.this, "Save Bio", Toast.LENGTH_SHORT).show());
        // TODO set image
//        skillsEditableElementList.addElementTextView.setOnClickListener(view -> {
//            Intent intent = new Intent(QuickBio.this, AddSkills.class);
//            startActivity(intent);
//        });
//
//        interestsEditableElementList.addElementTextView.setOnClickListener(view -> {
//            Intent intent = new Intent(QuickBio.this, AddInterests.class);
//            startActivity(intent);
//        });
//
//        saveBioButton.setOnClickListener(view -> Toast.makeText(QuickBio.this, "Save Bio", Toast.LENGTH_SHORT).show());
//        // TODO set image

    }
}

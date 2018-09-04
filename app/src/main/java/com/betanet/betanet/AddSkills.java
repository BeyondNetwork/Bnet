package com.betanet.betanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class AddSkills extends AppCompatActivity {

    SearchView skillsSearchView;
    Button backToBioButton;
    Button saveSkillsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        skillsSearchView = (SearchView) findViewById(R.id.skillsSearchView);
        backToBioButton = (Button) findViewById(R.id.backToBioButton);
        saveSkillsButton = (Button) findViewById(R.id.saveInterestsButton);

        backToBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddSkills.this, QuickBio.class);
                startActivity(intent);
            }
        });
        saveSkillsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddSkills.this, "Save Skills", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

package com.betanet.betanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

public class AddInterests extends AppCompatActivity {

    SearchView interestsSearchView;
    Button backToBioButton;
    Button saveInterestsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interests);
        interestsSearchView = (SearchView) findViewById(R.id.skillsSearchView);
        backToBioButton = (Button) findViewById(R.id.backToBioButton);
        saveInterestsButton = (Button) findViewById(R.id.saveInterestsButton);

        backToBioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddInterests.this, QuickBio.class);
                startActivity(intent);
            }
        });
        saveInterestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddInterests.this, "Save Interests", Toast.LENGTH_SHORT).show();
            }
        });

    }

}

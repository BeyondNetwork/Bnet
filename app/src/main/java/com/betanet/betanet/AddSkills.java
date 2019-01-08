package com.betanet.betanet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddSkills extends AppCompatActivity {
    private static final String TAG = "AddSkills";

    Button backToBioButton;
    Button saveSkillsButton;
    ArrayList<String> skills = new ArrayList<>();
    ArrayList<ValueWithRemoveButton> skillLayoutList = new ArrayList<>();
    LinearLayout skillLinearLayout;
    SearchView skillsSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skills);
        backToBioButton = findViewById(R.id.skillsBackToBioButton);
        saveSkillsButton = findViewById(R.id.saveSkillsButton);
        skillLinearLayout = findViewById(R.id.skillLinearLayout);
        skillsSearchView = findViewById(R.id.skillsSearchView);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        skillsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        handleIntent(getIntent());

    }
    public void onClickSkillsBackToBio(View v) {
        onClickSaveSkills(v);
        Intent intent = new Intent(AddSkills.this, QuickBio.class);
        startActivity(intent);
    }
    public void onClickSaveSkills(View v) {
        Toast.makeText(AddSkills.this, "Saved your skills!", Toast.LENGTH_SHORT).show();
        // TODO
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e(TAG, "handleIntent: action_search: " + query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String skill = intent.getDataString();
            Log.e(TAG, "handleIntent: skill = " + skill);
            Uri data = intent.getData();
            Log.e(TAG, "handleIntent: data = " + data);
            addSkill(skill);
        }
    }

    public void addSkill(final String skill) {
        Log.e(TAG, "addSkill: " + skill);
        if (!skills.contains(skill)) {
            skills.add(skill);
            ValueWithRemoveButton l = new ValueWithRemoveButton(this, skill);
            skillLayoutList.add(l);
            l.setOnChangeListener(val -> {
                skills.remove(val);
                populateSkillLayout();
            });
            populateSkillLayout();
        }
    }

    public void populateSkillLayout() {
        skillLinearLayout.removeAllViews();
        for (ValueWithRemoveButton l : skillLayoutList) {
            if(skills.contains(l.getValue()))
                skillLinearLayout.addView(l);
        }
    }
}
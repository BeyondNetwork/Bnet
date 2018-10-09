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
//    ArrayList<LinearLayout> skillLayoutList = new ArrayList<>();
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
//            doMySearch(query);
        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
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
//            LinearLayout l = new LinearLayout(this);
//            l.setOrientation(LinearLayout.HORIZONTAL);
//            TextView skillNameTV = new TextView(this);
//            skillNameTV.setText(skill);
//            skillNameTV.setPadding(2,2,2,2);
//            skillNameTV.setTextSize(24);
//            ImageButton removeButton = new ImageButton(this);
//            removeButton.setImageResource(R.drawable.ic_close_black_24dp);
//            removeButton.setBackgroundColor(Color.TRANSPARENT);
//            l.addView(skillNameTV);
//            l.addView(removeButton);
//            removeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    v.getParent();
//                    int index = skillLayoutList.indexOf(v);
//                    Log.e(TAG, "onClick: index: " +index);
//                    Log.e(TAG, "onClick: skill to be removed = " + skills.get(index));
//                    removeASkill(v.getRootView());
//                }
//            });
            skillLayoutList.add(l);
            ValueWithRemoveButton.OnChangeListener listener = new ValueWithRemoveButton.OnChangeListener() {
                @Override
                public void onChange(String val) {
                    skills.remove(val);
                    populateSkillLayout();
                }
            };
            l.setOnChangeListener(listener);
            populateSkillLayout();
        }
    }

    public void populateSkillLayout() {
        skillLinearLayout.removeAllViews();
//        for (LinearLayout l : skillLayoutList) {
//            skillLinearLayout.addView(l);
//        }
        for (ValueWithRemoveButton l : skillLayoutList) {
            if(skills.contains(l.getValue()))
                skillLinearLayout.addView(l);
        }
    }
}
package com.betanet.betanet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddAchievements extends AppCompatActivity {
    private static final String TAG = "AddAchievements";

    Button backToBioButton;
    Button saveAchievementsButton;
    ArrayList<String> achievements = new ArrayList<>();
    ArrayList<ValueWithRemoveButton> achievementLayoutList = new ArrayList<>();
    LinearLayout achievementLinearLayout;
    EditText achievementsEditText;
//    SearchView achievementsSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievements);
        backToBioButton = findViewById(R.id.achievementsBackToBioButton);
        saveAchievementsButton = findViewById(R.id.saveAchievementsButton);
        achievementLinearLayout = findViewById(R.id.achievementLinearLayout);
        achievementsEditText = findViewById(R.id.achievementsEditText);
//        achievementsSearchView = findViewById(R.id.achievementsSearchView);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        achievementsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        handleIntent(getIntent());

        achievementsEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addAchievement(achievementsEditText.getText().toString().trim());
                    achievementsEditText.setText("");
                    handled = true;
                }
                return handled;
            }
        });
        achievementsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    achievementsEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    achievementsEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_achievement, 0, 0, 0);
                }
            }
        });
    }

    public void onClickAchievementsBackToBio(View v) {
        onClickSaveAchievements(v);
        Intent intent = new Intent(AddAchievements.this, QuickBio.class);
        startActivity(intent);
    }

    public void onClickSaveAchievements(View v) {
        Toast.makeText(AddAchievements.this, "Saved your achievements!", Toast.LENGTH_SHORT).show();
        // TODO
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        setIntent(intent);
//        handleIntent(intent);
//    }

//    private void handleIntent(Intent intent) {
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            Log.e(TAG, "handleIntent: action_search: " + query);
//        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            String achievement = intent.getDataString();
//            Log.e(TAG, "handleIntent: achievement = " + achievement);
//            Uri data = intent.getData();
//            Log.e(TAG, "handleIntent: data = " + data);
//            addAchievement(achievement);
//        }
//    }

    public void addAchievement(final String achievement) {
        Log.e(TAG, "addAchievement: " + achievement);
        if (!achievements.contains(achievement)) {
            achievements.add(achievement);
            ValueWithRemoveButton l = new ValueWithRemoveButton(this, achievement);
            achievementLayoutList.add(l);
            l.setOnChangeListener(val -> {
                achievements.remove(val);
                populateAchievementLayout();
            });
            populateAchievementLayout();
        }
    }

    public void populateAchievementLayout() {
        achievementLinearLayout.removeAllViews();
        for (ValueWithRemoveButton l : achievementLayoutList) {
            if(achievements.contains(l.getValue()))
                achievementLinearLayout.addView(l);
        }
    }
}
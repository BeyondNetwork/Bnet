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


public class AddInterests extends AppCompatActivity {
    private static final String TAG = "AddInterests";

    Button backToBioButton;
    Button saveInterestsButton;
    ArrayList<String> interests = new ArrayList<>();
    ArrayList<ValueWithRemoveButton> interestLayoutList = new ArrayList<>();
    LinearLayout interestLinearLayout;
    SearchView interestsSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interests);
        backToBioButton = findViewById(R.id.interestsBackToBioButton);
        saveInterestsButton = findViewById(R.id.saveInterestsButton);
        interestLinearLayout = findViewById(R.id.interestLinearLayout);
        interestsSearchView = findViewById(R.id.interestsSearchView);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        interestsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        handleIntent(getIntent());

    }
    public void onClickInterestsBackToBio(View v) {
        onClickSaveInterests(v);
        Intent intent = new Intent(AddInterests.this, QuickBio.class);
        startActivity(intent);
    }
    public void onClickSaveInterests(View v) {
        Toast.makeText(AddInterests.this, "Saved your interests!", Toast.LENGTH_SHORT).show();
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
            String interest = intent.getDataString();
            Log.e(TAG, "handleIntent: interest = " + interest);
            Uri data = intent.getData();
            Log.e(TAG, "handleIntent: data = " + data);
            addInterest(interest);
        }
    }

    public void addInterest(final String interest) {
        Log.e(TAG, "addInterest: " + interest);
        if (!interests.contains(interest)) {
            interests.add(interest);
            ValueWithRemoveButton l = new ValueWithRemoveButton(this, interest);
            interestLayoutList.add(l);
            l.setOnChangeListener(val -> {
                interests.remove(val);
                populateInterestLayout();
            });
            populateInterestLayout();
        }
    }

    public void populateInterestLayout() {
        interestLinearLayout.removeAllViews();
        for (ValueWithRemoveButton l : interestLayoutList) {
            if(interests.contains(l.getValue()))
                interestLinearLayout.addView(l);
        }
    }
}
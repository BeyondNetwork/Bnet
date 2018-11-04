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


public class AddProfession extends AppCompatActivity {
    private static final String TAG = "AddProfession";

    Button backToBioButton;
    Button saveProfessionsButton;
    ArrayList<String> professions = new ArrayList<>();
    ArrayList<ValueWithRemoveButton> professionLayoutList = new ArrayList<>();
    LinearLayout professionLinearLayout;
    SearchView professionsSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profession);
        backToBioButton = findViewById(R.id.professionsBackToBioButton);
        saveProfessionsButton = findViewById(R.id.saveProfessionsButton);
        professionLinearLayout = findViewById(R.id.professionLinearLayout);
        professionsSearchView = findViewById(R.id.professionsSearchView);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        professionsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        handleIntent(getIntent());

    }
    public void onClickProfessionsBackToBio(View v) {
        onClickSaveProfessions(v);
        Intent intent = new Intent(AddProfession.this, QuickBio.class);
        startActivity(intent);
    }
    public void onClickSaveProfessions(View v) {
        Toast.makeText(AddProfession.this, "Saved your professions!", Toast.LENGTH_SHORT).show();
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
            String profession = intent.getDataString();
            Log.e(TAG, "handleIntent: profession = " + profession);
            Uri data = intent.getData();
            Log.e(TAG, "handleIntent: data = " + data);
            addProfession(profession);
        }
    }

    public void addProfession(final String profession) {
        Log.e(TAG, "addProfession: " + profession);
        if (!professions.contains(profession)) {
            professions.add(profession);
            ValueWithRemoveButton l = new ValueWithRemoveButton(this, profession);
            professionLayoutList.add(l);
            l.setOnChangeListener(val -> {
                professions.remove(val);
                populateProfessionLayout();
            });
            populateProfessionLayout();
        }
    }

    public void populateProfessionLayout() {
        professionLinearLayout.removeAllViews();
        for (ValueWithRemoveButton l : professionLayoutList) {
            if(professions.contains(l.getValue()))
                professionLinearLayout.addView(l);
        }
    }
}
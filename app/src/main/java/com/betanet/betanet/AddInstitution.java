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


public class AddInstitution extends AppCompatActivity {
    private static final String TAG = "AddInstitution";

    Button backToBioButton;
    Button saveInstitutionsButton;
    ArrayList<String> institutions = new ArrayList<>();
    ArrayList<ValueWithRemoveButton> institutionLayoutList = new ArrayList<>();
    LinearLayout institutionLinearLayout;
    EditText institutionsEditText;
//    SearchView institutionsSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_institution);
        backToBioButton = findViewById(R.id.institutionsBackToBioButton);
        saveInstitutionsButton = findViewById(R.id.saveInstitutionsButton);
        institutionLinearLayout = findViewById(R.id.institutionLinearLayout);
        institutionsEditText = findViewById(R.id.institutionsEditText);
//        institutionsSearchView = findViewById(R.id.institutionsSearchView);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        institutionsSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        handleIntent(getIntent());

        institutionsEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addInstitution(institutionsEditText.getText().toString().trim());
                    institutionsEditText.setText("");
                    handled = true;
                }
                return handled;
            }
        });
        institutionsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    institutionsEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    institutionsEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_institution, 0, 0, 0);
                }
            }
        });
    }

    public void onClickInstitutionsBackToBio(View v) {
        onClickSaveInstitutions(v);
        Intent intent = new Intent(AddInstitution.this, QuickBio.class);
        startActivity(intent);
    }

    public void onClickSaveInstitutions(View v) {
        Toast.makeText(AddInstitution.this, "Saved your institutions!", Toast.LENGTH_SHORT).show();
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
//            String institution = intent.getDataString();
//            Log.e(TAG, "handleIntent: institution = " + institution);
//            Uri data = intent.getData();
//            Log.e(TAG, "handleIntent: data = " + data);
//            addInstitution(institution);
//        }
//    }

    public void addInstitution(final String institution) {
        Log.e(TAG, "addInstitution: " + institution);
        if (!institutions.contains(institution)) {
            institutions.add(institution);
            ValueWithRemoveButton l = new ValueWithRemoveButton(this, institution);
            institutionLayoutList.add(l);
            l.setOnChangeListener(val -> {
                institutions.remove(val);
                populateInstitutionLayout();
            });
            populateInstitutionLayout();
        }
    }

    public void populateInstitutionLayout() {
        institutionLinearLayout.removeAllViews();
        for (ValueWithRemoveButton l : institutionLayoutList) {
            if(institutions.contains(l.getValue()))
                institutionLinearLayout.addView(l);
        }
    }
}
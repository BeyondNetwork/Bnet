package com.betanet.betanet;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupsActivity extends AppCompatActivity {

    ImageView groups, feeds, profile;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groups = findViewById(R.id.groups);
        feeds = findViewById(R.id.feeds);
        profile = findViewById(R.id.profile);

        groups.setOnClickListener(view -> {
            groups.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.groups_red));
            feeds.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.feeds_gray));
            profile.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.profile_gray));
        });

        feeds.setOnClickListener(view -> {
            groups.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.groups_gray));
            feeds.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.feeds_red));
            profile.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.profile_gray));
        });

        profile.setOnClickListener(view -> {
            groups.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.groups_gray));
            feeds.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.feeds_gray));
            profile.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.profile_red));
        });

        myDialog = new Dialog(this);
    }

    public void showPopup(View view) {
        Button close;
        myDialog.setContentView(R.layout.group_join_popup);

        close = myDialog.findViewById(R.id.close_btn);
        close.setOnClickListener(v -> myDialog.dismiss());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
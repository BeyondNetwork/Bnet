package com.betanet.betanet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start extends AppCompatActivity {

    Button registerButton;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        registerButton = findViewById(R.id.registerButton);
        signInButton = findViewById(R.id.signInButton);

        registerButton.setOnClickListener((view) -> {
            Intent intent = new Intent(Start.this, Register.class);
            startActivity(intent);
        });
        signInButton.setOnClickListener((view) -> {
            Intent intent = new Intent(Start.this, Login.class);
            startActivity(intent);
        });
    }

    public void jump(View v) {
        Intent i = new Intent(this, AddSkills.class);
        startActivity(i);
    }
}
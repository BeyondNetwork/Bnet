package com.betanet.betanet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ValueLayout extends ConstraintLayout {
    TextView valueTV;
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ValueLayout(Context context) {
        super(context);
    }

    public ValueLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ValueLayout(Context context, final String value) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.value_with_remove_button_layout, this);
        this.value = value;
        valueTV = findViewById(R.id.valueTV);
        valueTV.setText(this.value);
    }
}

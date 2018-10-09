package com.betanet.betanet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ValueWithRemoveButton extends ConstraintLayout {
    ImageButton removeButton;
    TextView valueTV;
    private String value;
    private OnChangeListener mListener = null;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ValueWithRemoveButton(Context context) {
        super(context);
    }

    public ValueWithRemoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueWithRemoveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ValueWithRemoveButton(Context context, final String value) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.value_with_remove_button_layout, this);
        this.value = value;
        removeButton = findViewById(R.id.removeButton);
        valueTV = findViewById(R.id.valueTV);
        valueTV.setText(this.value);
        removeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChange(value);
                }
            }
        });
    }
    public void setOnChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    public interface OnChangeListener {
        void onChange(String val);
    }

}

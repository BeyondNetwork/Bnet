package com.betanet.betanet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class ValueWithRemoveButton extends ConstraintLayout {
    public ValueWithRemoveButton(Context context) {
        super(context);
    }

    public ValueWithRemoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ValueWithRemoveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ValueWithRemoveButton(Context context, String value) {
        super(context);

    }

}

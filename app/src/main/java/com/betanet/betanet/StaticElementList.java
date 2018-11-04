package com.betanet.betanet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StaticElementList extends ConstraintLayout {
    private TextView elementTextView;
    private LinearLayout elementLinearLayout;
    private ArrayList<String> elements;
    private String elementName = "Elements";

    public void setElementName(String elementName) {
        this.elementName = elementName;
        populateData();
    }

    public ArrayList<String> getElements() {
        return elements;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
        populateData();
    }

    public String getElementName() {
        return elementName;
    }

    public StaticElementList(Context context) {
        super(context);
    }

    public StaticElementList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticElementList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StaticElementList(Context context, final String elementName, final ArrayList<String> elements) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.editable_element_list, this);
        elementTextView = findViewById(R.id.elementTextView);
        elementLinearLayout = findViewById(R.id.elementLinearLayout);
        this.elementName = elementName;
        this.elements = elements;
        populateData();
    }

    void populateData() {
        elementTextView.setText(elementName);
        elementLinearLayout.removeAllViews();
        if(elements!=null) {
            for (String element : elements) {
                elementLinearLayout.addView(new ValueLayout(getContext(), element));
            }
        }
    }
}
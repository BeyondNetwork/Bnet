package com.betanet.betanet;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditableElementList extends ConstraintLayout {

    TextView elementTextView, addElementTextView;
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

    public EditableElementList(Context context) {
        super(context);
    }

    public EditableElementList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditableElementList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditableElementList(Context context, final String elementName, final ArrayList<String> elements) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.editable_element_list, this);
        elementTextView = findViewById(R.id.elementTextView);
        addElementTextView = findViewById(R.id.addElementTextView);
        elementLinearLayout = findViewById(R.id.elementLinearLayout);
        this.elementName = elementName;
        this.elements = elements;
        populateData();
    }

    void saveData() {
        // TODO
    }

    void populateData() {
        elementTextView.setText(elementName);
        addElementTextView.setText("+ Add ".concat(elementName));

        elementLinearLayout.removeAllViews();
        if(elements!=null) {
            for (String element : elements) {
                ValueWithRemoveButton l = new ValueWithRemoveButton(getContext(), element);
                elementLinearLayout.addView(l);
                l.setOnChangeListener(val -> {
                    elements.remove(val);
                    saveData();
                    populateData();
                });
            }
        }
    }

}
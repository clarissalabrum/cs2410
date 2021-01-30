package com.example.trees;

import android.content.Context;
import android.text.Editable;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class ConstraintField extends LinearLayout {
    AppCompatEditText editText;
    AppCompatTextView textView;

    public ConstraintField(Context context, String text) {
        super(context);
        setOrientation(VERTICAL);

        textView = new AppCompatTextView(context);
        textView.setText(text);

        editText = new AppCompatEditText(context);

        addView(textView);
        addView(editText);
    }

    public int getConstraint() {
        final String constraint = editText.getText().toString();
        int con;

        try {
            con = Integer.parseInt(constraint);
        } catch (Exception e){
            con = 100;
        }
        return con;
    }
}

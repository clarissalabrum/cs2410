package com.example.calculator;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatTextView;

public class PanelView extends AppCompatTextView {
    private String expression = "";

    public PanelView(Context context){
        super(context);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(0, 1, 1);
        params.columnSpec = GridLayout.spec(0, 3, 1);
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        setTextSize(24);
        setTextColor(Color.BLACK);
    }

    public void setExpression(String expression) {
        this.expression = expression;
        this.setText(expression);
    }

    public String getExpression() {
        return expression;
    }
}

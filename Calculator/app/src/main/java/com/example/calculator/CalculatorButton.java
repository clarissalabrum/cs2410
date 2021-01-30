package com.example.calculator;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

public class CalculatorButton extends AppCompatButton {


    public CalculatorButton(Context context, ButtonData data, OnClickListener onClickListener){
        super(context);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(data.row, 1, 1);
        params.columnSpec = GridLayout.spec(data.col,data.colSpan, 1);
        setLayoutParams(params);
        setText(data.text);
        setOnClickListener(onClickListener);

        //style eval button
        if(data.type == ButtonData.ButtonType.EVALUATE){
            setBackgroundColor(Color.BLACK);
            setTextColor(Color.WHITE);
            setTextSize(60);
            setGravity(Gravity.CENTER);
        }

        //style operator and clear buttons
        if (data.type == ButtonData.ButtonType.OPERATOR
                || data.type == ButtonData.ButtonType.CLEAR){
            setBackgroundColor(getResources().getColor(R.color.colorOperation, null));
            setTextColor(Color.WHITE);
            setTextSize(40);
        }

        //style number buttons
        if (data.type == ButtonData.ButtonType.NUMBER){
            setBackgroundColor(getResources().getColor(R.color.colorNumber, null));
            setTextColor(Color.BLACK);
            setTextSize(40);
        }


    }
}

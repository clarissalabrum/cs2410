package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static com.example.calculator.Evaluate.evaluate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        //create grid layout and set parameters
        GridLayout calculatorLayout = new GridLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        calculatorLayout.setLayoutParams(params);
        calculatorLayout.setColumnCount(4);

        //create panel and add it to the grid layout
        final PanelView panel = new PanelView(this);
        calculatorLayout.addView(panel);

        //create each button with individual onclick listeners and add button view to grid layout
        for (final ButtonData data: buttonData){
            CalculatorButton button = new CalculatorButton(
                    this, data,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (data.type == ButtonData.ButtonType.EVALUATE){
                                    if (!panel.getExpression().equals("")){
                                        panel.setExpression(evaluate(panel.getExpression()));
                                    }
                            } else if (data.type == ButtonData.ButtonType.CLEAR) {
                                panel.setExpression("");
                            } else {
                                panel.setExpression(panel.getExpression() + data.text);
                            }

                        }
                    }
            );
            calculatorLayout.addView(button);
        }

        //set layout
        mainLayout.addView(calculatorLayout);
        setContentView(mainLayout);
    }

    //initialize button information
    private ArrayList<ButtonData> buttonData= new ArrayList<ButtonData>() {
        {
            add(new ButtonData("C", 0, 3, 1, ButtonData.ButtonType.CLEAR));
            add(new ButtonData("1", 1, 0, 1));
            add(new ButtonData("2", 1, 1, 1));
            add(new ButtonData("3", 1, 2, 1));
            add(new ButtonData(" / ", 1, 3, 1, ButtonData.ButtonType.OPERATOR));
            add(new ButtonData("4", 2, 0, 1));
            add(new ButtonData("5", 2, 1, 1));
            add(new ButtonData("6", 2, 2, 1));
            add(new ButtonData(" * ", 2, 3, 1, ButtonData.ButtonType.OPERATOR));
            add(new ButtonData("7", 3, 0, 1));
            add(new ButtonData("8", 3, 1, 1));
            add(new ButtonData("9", 3, 2, 1));
            add(new ButtonData(" - ", 3, 3, 1, ButtonData.ButtonType.OPERATOR));
            add(new ButtonData("0", 4, 1, 2));
            add(new ButtonData(".", 4, 0, 1));
            add(new ButtonData(" + ", 4, 3, 1, ButtonData.ButtonType.OPERATOR));
            add(new ButtonData("=", 5, 0, 4, ButtonData.ButtonType.EVALUATE));
        }
    };
}
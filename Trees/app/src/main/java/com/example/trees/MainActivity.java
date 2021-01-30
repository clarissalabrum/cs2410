package com.example.trees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main Layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // Constraint Fields
        ConstraintField maxLength = new ConstraintField(this, "Max Length");
        ConstraintField minLength = new ConstraintField(this, "Min Length");
        ConstraintField maxAngle = new ConstraintField(this, "Max Angle (max:90)");
        ConstraintField minAngle = new ConstraintField(this, "Min Angle (min:-90)");
        ConstraintField maxTrunk = new ConstraintField(this, "Starting Trunk Width");
        mainLayout.addView(maxLength);
        mainLayout.addView(minLength);
        mainLayout.addView(maxAngle);
        mainLayout.addView(minAngle);
        mainLayout.addView(maxTrunk);

        // Generate Tree Button
        AppCompatButton createButton = new AppCompatButton(this);
        createButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, TreeActivity.class);
            int[] constraints = {maxLength.getConstraint(), minLength.getConstraint(),
                                    maxAngle.getConstraint(), minAngle.getConstraint(),
                                    maxTrunk.getConstraint()};

            //int[] constraints = {200, 200, 65, -65, 100};
            intent.putExtra("constraints", constraints);

            startActivity(intent);
        });
        createButton.setText("Generate Tree");
        mainLayout.addView(createButton);

        // Set Layout
        setContentView(mainLayout);
    }
}
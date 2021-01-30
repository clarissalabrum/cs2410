package com.example.trees;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TreeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int[] constraints = intent.getIntArrayExtra("constraints");

        DrawingView draw = new DrawingView(this);
        Tree tree = new Tree(constraints, 540, 1731);
        Tree.Branch root = tree.getTree();
        addBranch(root, draw);


        GradientDrawable background = new GradientDrawable();
        background.setColors(new int[]{Color.BLUE, Color.BLACK});

        draw.setBackground(background);

        setContentView(draw);
    }

    //method to recurse through tree and add each branch to the drawing view
    private void addBranch(Tree.Branch currentBranch, DrawingView draw){
        draw.addLine(new Line(currentBranch.getX1(), currentBranch.getY1(),
                currentBranch.getX2(), currentBranch.getY2(), currentBranch.getWidth()));
        if (currentBranch.hasLeft()) addBranch(currentBranch.getLeft(), draw);
        if (currentBranch.hasRight()) addBranch(currentBranch.getRight(), draw);
    }
}

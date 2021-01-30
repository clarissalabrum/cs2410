package com.example.trees;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import java.util.ArrayList;

public class DrawingView extends View {
    private Paint paint = new Paint();
    private ArrayList<Line> lines = new ArrayList<>();
    private Line currentLine;
    public DrawingView(Context context){
        super(context);
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.GREEN);
        //setBackgroundColor(Color.GREEN);
        background.draw(new Canvas());
    }

    public void addLine(Line line){
        currentLine = line;
        lines.add(currentLine);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.truck,null));
        lines.forEach(line -> line.draw(canvas, paint));
    }

    public int getStartX(){
        Canvas canvas = new Canvas();
        double x = canvas.getWidth() / 2.0;
        return (int) Math.round(x);
    }

    public int getStartY(){
        Canvas canvas = new Canvas();
        double y = canvas.getHeight();
        return (int) Math.round(y);
    }
}

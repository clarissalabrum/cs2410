package com.example.trees;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line {
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int width;

    public Line(int x1, int y1, int x2, int y2, int width){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.width = width;
    }

    public void draw (Canvas canvas, Paint paint){
        paint.setStrokeWidth(width);

        canvas.drawLine(x1, y1, x2, y2, paint);
    }
}

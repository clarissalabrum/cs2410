package com.example.contacts.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.contacts.R;
import com.example.contacts.models.Contact;

import java.net.URL;
import java.net.URLStreamHandlerFactory;

public class CircleDisplay extends View {
    Paint paint = new Paint();
    String letter;
    int width = 40;
    String imageUri;

    public CircleDisplay(Context context, String letter) {
        super(context);
        this.letter = letter.toUpperCase();
        float density = getResources().getDisplayMetrics().density;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int)(width*density) , (int)(width*density));
        setLayoutParams(params);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int halfWidth = width / 2;
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark, null));
        float density = getResources().getDisplayMetrics().density;
        canvas.drawCircle(halfWidth * density, halfWidth * density, halfWidth * density, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(24*density);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(letter, (halfWidth * density), (halfWidth * density) + halfWidth, paint);
    }

}


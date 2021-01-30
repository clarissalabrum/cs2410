package com.example.contacts.components;

import android.content.Context;
import android.net.Uri;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.contacts.R;
import com.google.android.material.button.MaterialButton;

public class ImageSelector extends FrameLayout {
    String imageUri = "";
    AppCompatImageView imageView;

    public interface ImageSelectOnClickListener {
        public void onClick();
    }

    public ImageSelector(Context context, ImageSelectOnClickListener listener) {
        this(context, listener, "");
    }
    public ImageSelector(Context context, ImageSelectOnClickListener listener, String imageUri) {
        super(context);
        setBackgroundColor(getResources().getColor(R.color.darkBackgroundColor, null));
        this.imageUri = imageUri;
        imageView = new AppCompatImageView(context);
        setImageUri(imageUri);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 700);
        imageView.setLayoutParams(params);
        setOnClickListener((view -> {
            listener.onClick();
        }));
        addView(imageView);
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
        if (imageUri.equals("")) {
            imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_240);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView.setImageURI(Uri.parse(imageUri));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    public String getImageUri() {
        return imageUri;
    }
}

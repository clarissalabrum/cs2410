package com.example.contacts.components;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.contacts.R;
import com.example.contacts.models.Contact;

public class ContactImage extends FrameLayout {

    public ContactImage(Context context, Contact contact) {
        super(context);
        AppCompatImageView imageView = new AppCompatImageView(context);
        setBackgroundColor(getResources().getColor(R.color.darkBackgroundColor, null));
        if (contact.pictureUri.equals("")) {
            imageView.setImageResource(R.drawable.ic_baseline_person_240);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView.setImageURI(Uri.parse(contact.pictureUri));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 800);
        imageView.setLayoutParams(params);

        AppCompatTextView nameTextView = new AppCompatTextView(context);
        nameTextView.setText(contact.name);
        nameTextView.setTextSize(36);
        nameTextView.setTextColor(Color.WHITE);
        nameTextView.setGravity(Gravity.BOTTOM);
        nameTextView.setPadding(24,24,24,12);



        addView(imageView);
        addView(nameTextView);
    }

//    public void setImageUri(String imageUri) {
//        this.imageUri = imageUri;
//        if (imageUri.equals("")) {
//            imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_240);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        } else {
//            imageView.setImageURI(Uri.parse(imageUri));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//    }
}

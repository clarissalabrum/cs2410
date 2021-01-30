package com.example.contacts.components;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.contacts.models.Contact;

public class ClickableLabel extends LinearLayout {

    public interface OnClick{
        public void call();
    }

    private Contact contact;
    OnClick listener;

    public ClickableLabel(Context context, Contact contact, OnClick onClick){
        super(context);
        this.contact = contact;
        setTag(contact.id);
        this.listener = onClick;
        createViews();
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        createViews();
    }

    private void createViews() {
        removeAllViews();
        // circle
        String letter = Character.toString(contact.name.charAt(0));
        CircleDisplay circleDisplay = new CircleDisplay(getContext(), letter);
        addView(circleDisplay);



        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(24, 24, 24, 0);
        setLayoutParams(params);

        AppCompatTextView nameTextView = new AppCompatTextView(getContext());
        nameTextView.setText(contact.name);
        nameTextView.setTextSize(30);
        nameTextView.setPadding(30,5,30,5);
        nameTextView.setGravity(Gravity.CENTER_VERTICAL);

        setOnClickListener(view -> {
            listener.call();
        });

        addView(nameTextView);
    }
}

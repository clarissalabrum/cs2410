package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //text views
        final AppCompatTextView textTwoDigit = new AppCompatTextView(this);
        final AppCompatTextView textNoun1 = new AppCompatTextView(this);
        final AppCompatTextView textNoun2 = new AppCompatTextView(this);
        final AppCompatTextView textAdj = new AppCompatTextView(this);
        final AppCompatTextView textCountry = new AppCompatTextView(this);
        final AppCompatTextView textBusiness = new AppCompatTextView(this);
        final AppCompatTextView textThreeDigit = new AppCompatTextView(this);
        final AppCompatTextView textVerb = new AppCompatTextView(this);

        textTwoDigit.setText("Two Digit Number");
        textNoun1.setText("Noun");
        textNoun2.setText("Noun");
        textAdj.setText("Adjective");
        textCountry.setText("Name of Country");
        textBusiness.setText("Name of Business");
        textThreeDigit.setText("Three Digit Number");
        textVerb.setText("Verb");

        //edit view
        final AppCompatEditText editTwoDigit = new AppCompatEditText(this);
        final AppCompatEditText editNoun1 = new AppCompatEditText(this);
        final AppCompatEditText editAdj = new AppCompatEditText(this);
        final AppCompatEditText editNoun2 = new AppCompatEditText(this);
        final AppCompatEditText editCountry = new AppCompatEditText(this);
        final AppCompatEditText editBusiness = new AppCompatEditText(this);
        final AppCompatEditText editThreeDigit = new AppCompatEditText(this);
        final AppCompatEditText editVerb = new AppCompatEditText(this);

        editTwoDigit.setLayoutParams(params);
        editNoun1.setLayoutParams(params);
        editAdj.setLayoutParams(params);
        editNoun2.setLayoutParams(params);
        editCountry.setLayoutParams(params);
        editBusiness.setLayoutParams(params);
        editThreeDigit.setLayoutParams(params);
        editVerb.setLayoutParams(params);

        //message view
        final AppCompatTextView message = new AppCompatTextView(MainActivity.this);

        //mad libs button
        final AppCompatButton button = new AppCompatButton(this);
        button.setText("Display Mad Lib");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String twoDigit = editTwoDigit.getText().toString();
                String noun1 = editNoun1.getText().toString();
                String adj = editAdj.getText().toString();
                String noun2 = editNoun2.getText().toString();
                String country = editCountry.getText().toString();
                String business = editBusiness.getText().toString();
                String threeDigit = editThreeDigit.getText().toString();
                String verb = editVerb.getText().toString();

                message.setText("In 2003 " + twoDigit + "% of people believed that " + noun1 +
                        "s are really " + adj + " " + noun2 + "s. Some " + noun2 + " activist in " + country +
                        " blamed " + business + " for the similarity causing " + threeDigit + " million dollars " +
                        "in legal fees. In 2014, it was uncovered that " + noun2 + " worshipers" +
                        " created " + noun1 + "s in secret laboratories in the 80's. When this made " +
                        "headlines it was nicknamed " + noun1 + "-" + noun2 + "-" + verb);
                layout.removeAllViews();
                layout.addView(message);
            }
        });


        //layout
        layout.addView(textTwoDigit);
        layout.addView(editTwoDigit);
        layout.addView(textNoun1);
        layout.addView(editNoun1);
        layout.addView(textAdj);
        layout.addView(editAdj);
        layout.addView(textNoun2);
        layout.addView(editNoun2);
        layout.addView(textCountry);
        layout.addView(editCountry);
        layout.addView(textBusiness);
        layout.addView(editBusiness);
        layout.addView(textThreeDigit);
        layout.addView(editThreeDigit);
        layout.addView(textVerb);
        layout.addView(editVerb);
        layout.addView(button);

        layout.setOrientation(LinearLayout.VERTICAL);

        setContentView(layout);
    }
}
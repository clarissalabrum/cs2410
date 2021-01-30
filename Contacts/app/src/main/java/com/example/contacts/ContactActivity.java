package com.example.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.example.contacts.components.ContactImage;
import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;
import com.example.contacts.presenter.ContactPresenter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

public class ContactActivity extends BaseActivity implements ContactPresenter.MVPView{
    ContactPresenter presenter;
    LinearLayout mainLayout;
    int ID;
    private final int UPDATE_POST = 1;
    private final int REQUEST_CALL_PERMISSIONS = 0;
    private final int REQUEST_SMS_PERMISSIONS = 1;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ContactPresenter(this);

        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        Intent intent = getIntent();
        ID = intent.getIntExtra("contactID", 0);

        presenter.displayContact(ID);

        setContentView(mainLayout);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            this.contact = contact;
            mainLayout.removeAllViews();
            FrameLayout contactLayout = new FrameLayout(this);

            FloatingActionButton fab = new FloatingActionButton(this);
            FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            fabParams.setMargins(0, 24, 24, 0);
            fabParams.gravity = (Gravity.RIGHT);
            fab.setLayoutParams(fabParams);
            fab.setImageResource(R.drawable.ic_baseline_edit_16);

            PopupMenu popupMenu = new PopupMenu(this, fab);
            popupMenu.getMenu().add("Edit");
            popupMenu.getMenu().add("Delete");

            fab.setOnClickListener((view) -> {
                popupMenu.show();
            });

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (menuItem.getTitle().toString().equals("Edit")) {
                        presenter.handleEditClick();
                    } else {
                        presenter.handleDeleteClick();
                    }
                    return false;
                }
            });

            //image
            AppCompatImageView imageView = new AppCompatImageView(this);
            imageView.setBackgroundColor(getResources().getColor(R.color.darkBackgroundColor, null));
            if (contact.pictureUri.equals("")) {
                imageView.setImageResource(R.drawable.ic_baseline_add_a_photo_240);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
            } else {
                imageView.setImageURI(Uri.parse(contact.pictureUri));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900);
            imageView.setLayoutParams(params);

            //name
            MaterialTextView nameTextView = new MaterialTextView(this, null, R.attr.textAppearanceHeadline3);
            nameTextView.setGravity(Gravity.BOTTOM);
            nameTextView.setText(contact.name);
            nameTextView.setTextColor(Color.WHITE);
            nameTextView.setPadding(24,24,24,12);

            //image and name in layout
            FrameLayout imageFrame = new FrameLayout(this);
            imageFrame.addView(imageView);
            imageFrame.addView(nameTextView);

            LinearLayout wrapper = new LinearLayout(this);
            wrapper.setOrientation(LinearLayout.VERTICAL);
            MaterialCardView cardView = new MaterialCardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(48,24,48,0);
            cardView.setLayoutParams(cardParams);

            //phone
            //call button
            AppCompatImageButton callButton = new AppCompatImageButton(this);
            callButton.setImageResource(R.drawable.ic_baseline_call_24);
            callButton.setBackgroundColor(Color.WHITE);
            callButton.setOnClickListener((view) -> {
                presenter.handleCallPressed(contact.phoneNumber.toString());
            });

            //phone number
            AppCompatTextView phoneNumberTextView = new AppCompatTextView(this);
            phoneNumberTextView.setTextSize(20);
            phoneNumberTextView.setText(contact.phoneNumber);
            LinearLayout.LayoutParams numberParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            numberParams.weight = 1;
            numberParams.gravity = Gravity.CENTER_VERTICAL;
            numberParams.setMargins(48,0,24,0);
            phoneNumberTextView.setLayoutParams(numberParams);

            //text button
            AppCompatImageButton textButton = new AppCompatImageButton(this);
            textButton.setImageResource(R.drawable.ic_baseline_textsms_24);
            textButton.setBackgroundColor(Color.WHITE);
            textButton.setOnClickListener((view) -> {
                presenter.handleTextPress(contact.phoneNumber.toString());
            });

            //phone layout
            LinearLayout phoneLayout = new LinearLayout(this);
            LinearLayout.LayoutParams phoneParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            phoneParams.setMargins(48,24,64,24);
            phoneLayout.setLayoutParams(phoneParams);
            phoneLayout.addView(callButton);
            phoneLayout.addView(phoneNumberTextView);
            phoneLayout.addView(textButton);
            wrapper.addView(phoneLayout);

            //email
            if (!contact.email.equals("")) {
                // email button
                AppCompatImageButton emailButton = new AppCompatImageButton(this);
                emailButton.setImageResource(R.drawable.ic_baseline_email_24);
                emailButton.setBackgroundColor(Color.WHITE);
                emailButton.setOnClickListener((view) -> {
                    presenter.handleEmailPressed(contact.email.toString());
                });

                //email display
                MaterialTextView emailTextView = new MaterialTextView(this);
                emailTextView.setTextSize(20);
                emailTextView.setText(contact.email);
                LinearLayout.LayoutParams emailParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                emailParams.weight = 1;
                emailParams.gravity = Gravity.CENTER_VERTICAL;
                emailParams.setMargins(48,0,24,0);
                emailTextView.setLayoutParams(emailParams);
                //email layout
                LinearLayout emailLayout = new LinearLayout(this);
                LinearLayout.LayoutParams emailLayoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                emailLayoutParams.setMargins(48,24,64,24);
                emailLayout.setLayoutParams(emailLayoutParams);
                emailLayout.addView(emailButton);
                emailLayout.addView(emailTextView);

                wrapper.addView(emailLayout);
            }
            LinearLayout contactLinearLayout = new LinearLayout(this);
            contactLinearLayout.setOrientation(LinearLayout.VERTICAL);
            cardView.addView(wrapper);
            contactLinearLayout.addView(imageFrame);
            contactLinearLayout.addView(cardView);
            contactLayout.addView(contactLinearLayout);
            contactLayout.addView(fab);
            mainLayout.addView(contactLayout);
        });
    }

    @Override
    public void goBackToContactsPage(Contact contact, boolean didDelete, boolean didUpdate) {
        Intent intent = new Intent();
        if (didDelete) {
            int id = contact.id;
            intent.putExtra("id", id);
            setResult(MainActivity.DELETE_RESULT, intent);
        } else if (didUpdate) {
            int id = contact.id;
            intent.putExtra("result", contact);
            setResult(MainActivity.UPDATE_RESULT, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, null);
        }
        finish();

    }

    @Override
    public void goToEditPage(Contact contact) {
        Intent intent = new Intent(this, CreateOrUpdateContactActivity.class);
        intent.putExtra("id", contact.id);
        startActivityForResult(intent, UPDATE_POST);
    }

    @Override
    public void displayDeleteConfimation() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Are you sure you want to delete this contact?")
                .setPositiveButton("Delete", (view, i) -> {
                    presenter.deleteContact();
                })
                .setNeutralButton("Cancel", (view, i) -> {
                    view.dismiss();
                })
                .show();
    }

    @Override
    public void makePhoneCall(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSIONS);
        }

    }

    @Override
    public void sendSMS(String number) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + number));
            startActivity(smsIntent);
        } else {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSIONS);
        }
    }

    @Override
    public void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        startActivity(emailIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_POST && resultCode == Activity.RESULT_OK) {
            Contact contact = (Contact) data.getSerializableExtra("result");
            presenter.handleContactUpdate(contact);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.handleBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.handleCallPressed(contact.phoneNumber.toString());
            }
        }

        if (requestCode == REQUEST_SMS_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.handleTextPress(contact.phoneNumber.toString());
            }
        }
    }
}

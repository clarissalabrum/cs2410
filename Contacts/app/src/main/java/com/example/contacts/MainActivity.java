package com.example.contacts;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.contacts.components.ClickableLabel;
import com.example.contacts.models.Contact;
import com.example.contacts.presenter.ContactsPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends BaseActivity implements ContactsPresenter.MVPView {
    private final int CREATE_NEW_CONTACT = 1;
    private final int MODIFY_CONTACT = 2;

    public static final int DELETE_RESULT = 1;
    public static final int UPDATE_RESULT = 2;


    ContactsPresenter presenter;
    FrameLayout mainLayout;
    LinearLayout contactsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ContactsPresenter(this);
        mainLayout = new FrameLayout(this);
        contactsLayout = new LinearLayout(this);
        ScrollView scrollView = new ScrollView(this);

        contactsLayout.setOrientation(LinearLayout.VERTICAL);

        FloatingActionButton fab = new FloatingActionButton(this);
        FrameLayout.LayoutParams fabParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fabParams.setMargins(0, 0, 48, 48);
        fabParams.gravity = (Gravity.RIGHT | Gravity.BOTTOM);
        fab.setLayoutParams(fabParams);
        fab.setImageResource(R.drawable.ic_baseline_add_24);

        fab.setOnClickListener(view -> {
            presenter.goToNewContactsPage();
        });


        scrollView.addView(contactsLayout);
        mainLayout.addView(scrollView);
        mainLayout.addView(fab);

        setContentView(mainLayout);
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            ClickableLabel clickableLabel = new ClickableLabel(
                    this,
                    contact,
                    () -> {
                        presenter.goToContactPage(contact);
                    });
            contactsLayout.addView(clickableLabel);
        });
    }

    @Override
    public void goToNewContactsPage() {
        Intent intent = new Intent(this, CreateOrUpdateContactActivity.class);
        startActivityForResult(intent, CREATE_NEW_CONTACT);
    }

    @Override
    public void goToContactPage(Contact contact) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("contactID", contact.id);
        startActivityForResult(intent, MODIFY_CONTACT);
    }

    @Override
    public void removeContactView(int id) {
        View view = contactsLayout.findViewWithTag(id);
        contactsLayout.removeView(view);
    }

    @Override
    public void updateContactView(Contact contact) {
        ClickableLabel view = contactsLayout.findViewWithTag(contact.id);
        view.setContact(contact);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_NEW_CONTACT && resultCode == Activity.RESULT_OK){
            Contact contact = (Contact) data.getSerializableExtra("result");
            presenter.onNewContactCreated(contact);
        }
        if (requestCode == MODIFY_CONTACT && resultCode == DELETE_RESULT) {
            int id = data.getIntExtra("id",-1);
            presenter.handleContactDelete(id);
        }
        if (requestCode == MODIFY_CONTACT && resultCode == UPDATE_RESULT) {
            Contact contact = (Contact) data.getSerializableExtra("result");
            presenter.handleContactUpdate(contact);
        }
    }
}
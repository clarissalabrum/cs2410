package com.example.contacts;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.contacts.components.ImageSelector;
import com.example.contacts.components.MaterialInput;
import com.example.contacts.models.Contact;
import com.example.contacts.presenter.CreateOrUpdateContactPresenter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrUpdateContactActivity extends BaseActivity implements CreateOrUpdateContactPresenter.MVPView {
    CreateOrUpdateContactPresenter presenter;
    private static int PICK_IMAGE = 1;
    private static int TAKE_PICTURE = 2;
    ImageSelector imageSelector;
    MaterialInput nameInput;
    MaterialInput phoneNumberInput;
    MaterialInput emailInput;
    String currentPhotoPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreateOrUpdateContactPresenter(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        presenter.loadContact(id);

        //main layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        //image view
        imageSelector = new ImageSelector(
                this,
                () -> {
                    new MaterialAlertDialogBuilder(this)
                            .setTitle("Choose Image")
                            .setItems(new CharSequence[]{"From Camera", "From Photos"}, (view, i) -> {
                                if (i == 0) {
                                    presenter.handleTakePicturePress();
                                } else {
                                    presenter.handleSelectPicturePress();
                                }
                            })
                            .show();
                }
        );

        //Input fields
        nameInput = new MaterialInput(this, "Name");
        phoneNumberInput = new MaterialInput(this, "Phone Number");
        emailInput = new MaterialInput(this, "Email");

        //save button
        MaterialButton saveButton = new MaterialButton(this,null, R.attr.materialButtonStyle);
        saveButton.setText("Save");
        saveButton.setOnClickListener(view -> {
            presenter.saveContact(
                    nameInput.getText().toString(),
                    phoneNumberInput.getText().toString(),
                    emailInput.getText().toString(),
                    imageSelector.getImageUri()
            );
        });

        //cancel button
        MaterialButton cancelButton = new MaterialButton(this,null, R.attr.borderlessButtonStyle);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener((view) -> {
            presenter.handleCancelPress();
        });

        //buttons layout
        LinearLayout buttonsLayout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(48,24 ,48, 0);
        buttonsLayout.setLayoutParams(params);
        buttonsLayout.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        buttonsLayout.addView(cancelButton);
        buttonsLayout.addView(saveButton);


        //add views
        mainLayout.addView(imageSelector);
        LinearLayout inputView = new LinearLayout(this);
        inputView.setOrientation(LinearLayout.VERTICAL);
        inputView.addView(nameInput);
        inputView.addView(phoneNumberInput);
        inputView.addView(emailInput);
        inputView.addView(buttonsLayout);
        ScrollView scrollView = new ScrollView(this);
        scrollView.addView(inputView);
        mainLayout.addView(scrollView);
        setContentView(mainLayout);
    }

    @Override
    public void goBackToPreviousPage(Contact contact) {
        if (contact == null) {
            setResult(Activity.RESULT_CANCELED, null);
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", contact);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void gotToPhotos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void takePicture() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName);
        currentPhotoPath = imageFile.getAbsolutePath();
        Uri photoUri = FileProvider.getUriForFile(this, "com.example.contacts.fileprovider", imageFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void displayImage(String uri) {
        imageSelector.setImageUri(uri);
    }

    @Override
    public void displayTitleError() {
        Snackbar.make(nameInput, "Name can not be blank.", Snackbar.LENGTH_SHORT).show();
        nameInput.setErrorEnabled(true);
        nameInput.setError("Name can not be blank.");
    }

    @Override
    public void displayNumberError() {
        Snackbar.make(phoneNumberInput, "Phone Number can only contain numbers.", Snackbar.LENGTH_SHORT).show();
        phoneNumberInput.setErrorEnabled(true);
        phoneNumberInput.setError("Phone Number can only contain numbers.");
    }

    @Override
    public void displayPhoneNumberError() {
        Snackbar.make(phoneNumberInput, "Phone Number can not be blank.", Snackbar.LENGTH_SHORT).show();
        phoneNumberInput.setErrorEnabled(true);
        phoneNumberInput.setError("Phone Number can not be blank.");
    }

    @Override
    public void displayEmailError() {
        Snackbar.make(emailInput, "Invalid Email.", Snackbar.LENGTH_SHORT).show();
        emailInput.setErrorEnabled(true);
        emailInput.setError("Invalid Email.");
    }

    @Override
    public void renderContact(Contact contact) {
        runOnUiThread(() -> {
            imageSelector.setImageUri(contact.pictureUri);
            nameInput.setText(contact.name);
            phoneNumberInput.setText(contact.phoneNumber);
            emailInput.setText(contact.email);
        });
    }

    @Override
    public void fixError() {
        emailInput.setErrorEnabled(false);
        nameInput.setErrorEnabled(false);
        phoneNumberInput.setErrorEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            String uri = data.getData().toString();
            presenter.handlePictureSelected(uri);
        }
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter.handlePictureSelected(currentPhotoPath);
        }
    }
}



package com.example.contacts.presenter;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

public class CreateOrUpdateContactPresenter {
    MVPView view;
    AppDatabase database;
    private static final int DEFAULT_ID = -1;
    Contact contact;

    public interface MVPView extends BaseMVPView {
        public void goBackToPreviousPage(Contact contact);
        public void gotToPhotos();
        void takePicture();
        void displayImage(String uri);
        void displayTitleError();
        void displayNumberError();
        void displayPhoneNumberError();
        void displayEmailError();
        void renderContact(Contact contact);
        void fixError();
    }

    public CreateOrUpdateContactPresenter(MVPView view){
        this.view = view;
        database = view.getContextDatabase();
    }

    public void saveContact(String name, String phoneNumber, String email, String uri){
        boolean fail = false;
        view.fixError();
        if (!email.equals("") && !email.contains("@")) {
            view.displayEmailError();
            fail = true;
        }
        if (!onlyDigits(phoneNumber)) {
            view.displayNumberError();
            fail = true;
        }
        if (phoneNumber == null || phoneNumber.equals("")){
            view.displayPhoneNumberError();
            fail = true;
        }
        if (name == null || name.equals("")){
            view.displayTitleError();
            fail = true;
        }
        if (fail) {return;}

        new Thread(() -> {
            Contact contactToSave;
            if(contact != null) {
                contactToSave = contact;
            } else {
                contactToSave = new Contact();
            }
            contactToSave.name = name;
            contactToSave.phoneNumber = phoneNumber;
            contactToSave.email = email;
            contactToSave.pictureUri = uri;
            if(contact != null) {
                database.getContactDao().update(contactToSave);
            } else {
                contactToSave.id = (int) database.getContactDao().insert(contactToSave);
            }
            view.goBackToPreviousPage(contactToSave);
        }).start();
    }

    public void handleCancelPress() { view.goBackToPreviousPage(null); }

    public void handleSelectPicturePress() {
        view.gotToPhotos();
    }

    public void handlePictureSelected(String uri) {
        view.displayImage(uri);
    }

    public void loadContact(int id) {
        if (id != DEFAULT_ID) {
            new Thread(() -> {
                contact = database.getContactDao().findById(id);
                view.renderContact(contact);
            }).start();
        }
    }
    public static boolean onlyDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            } else { return false; }
        }
        return false;
    }

    public void handleTakePicturePress(){
        view.takePicture();
    }
}

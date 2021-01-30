package com.example.contacts.presenter;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

public class ContactPresenter {
    ContactPresenter.MVPView view;
    AppDatabase database;
    Contact contact;
    boolean didUpdate = false;

    public interface MVPView extends BaseMVPView {
        public void renderContact(Contact contact);
        void goBackToContactsPage(Contact contact, boolean didDelete, boolean didUpdate);
        void goToEditPage(Contact contact);
        void displayDeleteConfimation();
        void makePhoneCall(String number);
        void sendSMS(String number);
        void sendEmail(String email);
    }

    public ContactPresenter(MVPView view){
        this.view = view;
        database = view.getContextDatabase();
    }

    public void displayContact(int ID){
        new Thread(() -> {
            contact = database.getContactDao().findById(ID);
            view.renderContact(contact);
        }).start();

    }

    public void deleteContact(){
        new Thread(() -> {
            database.getContactDao().delete(contact);
            view.goBackToContactsPage(contact, true, false);
        }).start();
    }

    public void handleDeleteClick() {
        view.displayDeleteConfimation();
    }
    public void handleEditClick() {
        view.goToEditPage(contact);
    }

    public void handleContactUpdate(Contact contact){
        didUpdate = true;
        this.contact = contact;
        view.renderContact(contact);
    }

    public void handleBackPressed() {
        view.goBackToContactsPage(contact, false, didUpdate);
    }

    public void handleCallPressed(String number) {
        view.makePhoneCall(number);
    }

    public void handleTextPress(String number) {
        view.sendSMS(number);
    }

    public void handleEmailPressed(String email) {
        view.sendEmail(email);
    }
}

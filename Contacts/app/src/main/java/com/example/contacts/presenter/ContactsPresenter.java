package com.example.contacts.presenter;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

import java.util.ArrayList;

public class ContactsPresenter {
    private MVPView view;
    private AppDatabase database;
    private ArrayList<Contact> contacts = new ArrayList<>();

    public interface MVPView extends BaseMVPView {
        public void renderContact(Contact contact);
        public void goToNewContactsPage();
        public void goToContactPage(Contact contact);
        void removeContactView(int id);
        void updateContactView(Contact contact);
    }

    public ContactsPresenter(MVPView view){
        this.view = view;
        database = view.getContextDatabase();
        refreshContacts();
    }

    public void goToNewContactsPage(){
        view.goToNewContactsPage();
    }

    public void goToContactPage(Contact contact){ view.goToContactPage(contact); }

    public void refreshContacts(){
        new Thread(() -> {
            contacts = (ArrayList<Contact>) database.getContactDao().getAllContacts();
            renderContacts();
        }).start();
    }

    public void onNewContactCreated(Contact contact){
        contacts.add(contact);
        view.renderContact(contact);
    }

    private void renderContacts() {
        contacts.forEach(contact -> {
            view.renderContact(contact);
        });
    }

    public void handleContactDelete(int id) {
        contacts.removeIf(contact -> {
            return contact.id == id;
        });
        view.removeContactView(id);
    }

    public void handleContactUpdate(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).id == contact.id) {
                contacts.set(i, contact);
            }
        }
        view.updateContactView(contact);
    }

}

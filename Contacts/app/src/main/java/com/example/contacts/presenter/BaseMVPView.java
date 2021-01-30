package com.example.contacts.presenter;

import com.example.contacts.database.AppDatabase;
import com.example.contacts.models.Contact;

import java.util.ArrayList;

public interface BaseMVPView {
    public AppDatabase getContextDatabase();
}

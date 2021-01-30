package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.contacts.database.AppDatabase;

public class BaseActivity extends AppCompatActivity {
    public AppDatabase getContextDatabase() {
        return Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contacts-database").build();
    }
}

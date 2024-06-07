package com.ammar.contactsmanagerapproomdatabase.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ammar.contactsmanagerapproomdatabase.db.entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {
    // Linking the DAO with our database
    public abstract ContactDAO getContactDAO();


}

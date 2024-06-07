package com.ammar.contactsmanagerapproomdatabase.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Contact.TABLE_NAME)
public class Contact {
    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_ID = "contact_id";
    public static final String COLUMN_NAME = "contact_name";
    public static final String COLUMN_EMAIL = "contact_email";


    @ColumnInfo(name = COLUMN_NAME)
    private String name;
    @ColumnInfo(name = COLUMN_EMAIL)
    private String email;
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Ignore
    public Contact() {
    }

    public Contact(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

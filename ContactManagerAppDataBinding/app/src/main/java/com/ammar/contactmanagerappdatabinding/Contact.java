package com.ammar.contactmanagerappdatabinding;

import android.database.Observable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// BaseObservable as we use 2 way data binding, so we need to notify the UI when the data changes
@Entity(tableName = "contacts")
public class Contact extends BaseObservable {

    @ColumnInfo(name = "contact_name")
    private String name;
    @ColumnInfo(name = "contact_email")
    private String email;

    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private int contactId;

    @Ignore
    public Contact() {
    }

    public Contact(String name, String email, int contactId) {
        this.name = name;
        this.email = email;
        this.contactId = contactId;
    }

    @Bindable
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
        // Notify the UI when the data changes
        notifyPropertyChanged(BR.contactId);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        // Notify the UI when the data changes
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        // Notify the UI when the data changes
        notifyPropertyChanged(BR.email);
    }
}

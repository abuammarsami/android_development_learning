package com.ammar.journalapp.util;

import android.app.Application;

// This class is used to store the current user of the application
// this will ensure that only one single user at runtime found on the application
public class JournalUser extends Application {
    private String userId;
    private String username;

    private static JournalUser instance;

    // Following the singleton Design Pattern
    public static JournalUser getInstance() {
        if (instance == null) {
            instance = new JournalUser();
        }
        return instance;
    }

    public  JournalUser() {
        // Empty constructor
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

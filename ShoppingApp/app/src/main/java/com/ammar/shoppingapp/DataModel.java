package com.ammar.shoppingapp;

public class DataModel {
    String username;
    String password;

    // Default Constructor is required for calls to DataSnapshot.getValue(User.class)
    public DataModel() {
    }

    public DataModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

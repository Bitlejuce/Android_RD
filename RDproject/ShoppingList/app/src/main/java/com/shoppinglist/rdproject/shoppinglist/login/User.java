package com.shoppinglist.rdproject.shoppinglist.login;

import android.net.Uri;

public class User {
    private String name;
    private String email;
    private Uri picUrl;

    public User() {
    }

    public User(String name, String email, Uri picUrl) {
        this.name = name;
        this.email = email;
        this.picUrl = picUrl;
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

    public Uri getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(Uri picUrl) {
        this.picUrl = picUrl;
    }
}


//{
//        "rules": {
//        ".read": "auth != null",
//        ".write": "auth != null"
//
//        }
//        }

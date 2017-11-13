package com.example.ffes.flex_framwork.noteview.data.Data;

/**
 * Created by user on 2017/7/25.
 */

public class User {

    private String name;
    private String account;
    private String info;
    private String photoUrl;

    public User() {
    }

    public User(String name, String account, String info, String photoUrl) {
        this.name = name;
        this.account = account;
        this.info = info;
        this.photoUrl=photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String email) {
        this.account = email;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/11/5.
 */

public class SharedNote {

    String id;
    int like;
    int look;
    int link;
    int comment;
    String title;
    String photoUrl;
    String selfpicture;
    String name;
    List<String> keywordlist;

    public SharedNote(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setSelfpicture(String selfpicture) {
        this.selfpicture = selfpicture;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public void setKeywordlist(List<String> keywordlist) {
        this.keywordlist = keywordlist;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public void setLook(int look) {
        this.look = look;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public int getComment() {
        return comment;
    }

    public int getLike() {
        return like;
    }

    public int getLink() {
        return link;
    }

    public int getLook() {
        return look;
    }

    public List<String> getKeywordlist() {
        return keywordlist;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getSelfpicture() {
        return selfpicture;
    }

    public String getId() {
        return id;
    }
}

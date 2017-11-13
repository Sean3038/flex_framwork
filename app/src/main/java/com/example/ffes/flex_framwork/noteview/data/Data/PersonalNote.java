package com.example.ffes.flex_framwork.noteview.data.Data;

/**
 * Created by user on 2017/8/3.
 */

public class PersonalNote {

    private String uid;
    private String coverurl;
    private String noteURL;
    private String title;
    private String color;
    private boolean isLink;
    private boolean isShare;

    public PersonalNote(){};

    public PersonalNote(String uid, String coverurl, String noteURL, String title, String color,boolean isLink,boolean isShare) {
        this.uid = uid;
        this.coverurl=coverurl;
        this.noteURL = noteURL;
        this.title = title;
        this.color = color;
        this.isLink=isLink;
        this.isShare=isShare;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getNoteURL(){
        return noteURL;
    }

    public void setNoteURL(String noteURL){
        this.noteURL = noteURL;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setShare(boolean share) {
        isShare = share;
    }

    public boolean isShare() {
        return isShare;
    }
}


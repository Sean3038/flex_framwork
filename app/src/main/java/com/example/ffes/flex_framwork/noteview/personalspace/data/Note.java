package com.example.ffes.flex_framwork.noteview.personalspace.data;

/**
 * Created by user on 2017/11/7.
 */

public class Note {
    String accountName;
    String personURL;
    String noteURL;
    String title;
    String color;
    int like;
    int look;
    int download;
    int message;

    public Note(String accountName, String personURL, String noteURL, String title, String color, int like, int look, int download, int message) {
        this.accountName = accountName;
        this.personURL = personURL;
        this.noteURL = noteURL;
        this.title = title;
        this.like = like;
        this.look = look;
        this.download = download;
        this.message = message;
        this.color = color;
    }

    public  String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUrl(){
        return personURL;
    }

    public void setUrl(String personURL){
        this.personURL = personURL;
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

    public int getLike(){
        return like;
    }

    public void setLike(int like){
        this.like = like;
    }

    public int getLook(){
        return look;
    }

    public void setLook(int look){
        this.look = look;
    }

    public int getDownloade(){
        return download;
    }

    public void setDownload(int download){
        this.download = download;
    }

    public int getMessage(){
        return message;
    }

    public void setMessage(int message){
        this.message = message;
    }
}



package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/8/27.
 */

public class Note{
    String id;
    List<Page> pagesList;
    String coverUrl;
    String title;
    String color;
    String authorID;
    String createdAt;
    String updatedAt;

    public Note(){}

    public Note(String id,List<Page> pagesList,String coverUrl,String title,String color,String authorID,String createdAt,String updatedAt){
        this.id=id;
        this.pagesList=pagesList;
        this.coverUrl=coverUrl;
        this.title=title;
        this.color=color;
        this.authorID=authorID;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }

    public String getId() {
        return id;
    }

    public List<Page> getPagesList() {
        return pagesList;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPagesList(List<Page> pagesList) {
        this.pagesList = pagesList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

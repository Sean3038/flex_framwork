package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/8/27.
 */

public class Note{
    String id;
    List<Page> pagesList;
    String coverUrl;
    TitleDetail titleDetail;
    String authorID;
    String createdAt;
    String updatedAt;

    public Note(){}

    public Note(String id,List<Page> pagesList,String coverUrl,TitleDetail titleDetail,String authorID,String createdAt,String updatedAt){
        this.id=id;
        this.pagesList=pagesList;
        this.coverUrl=coverUrl;
        this.titleDetail=titleDetail;
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

    public String getAuthorID() {
        return authorID;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public TitleDetail getTitleDetail() {
        return titleDetail;
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

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTitleDetail(TitleDetail titleDetail) {
        this.titleDetail = titleDetail;
    }
}

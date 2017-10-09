package com.example.ffes.flex_framwork.noteview.data;

/**
 * Created by Ffes on 2017/10/2.
 */

public class PageContent {
    int page;
    String pageNoteUrl;
    PageContent(){}
    public PageContent(int page, String pageNoteUrl){
        this.page=page;
        this.pageNoteUrl=pageNoteUrl;
    }

    public int getPage() {
        return page;
    }

    public String getPageNoteUrl() {
        return pageNoteUrl;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageNoteUrl(String pageNoteUrl) {
        this.pageNoteUrl = pageNoteUrl;
    }
}

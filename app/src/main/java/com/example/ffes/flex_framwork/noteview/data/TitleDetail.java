package com.example.ffes.flex_framwork.noteview.data;

/**
 * Created by Ffes on 2017/10/10.
 */

public class TitleDetail {
    int color;
    String title;

    TitleDetail(){}

    TitleDetail(int color,String title){
        this.color=color;
        this.title=title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}

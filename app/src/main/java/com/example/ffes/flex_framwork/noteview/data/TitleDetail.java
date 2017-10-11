package com.example.ffes.flex_framwork.noteview.data;

import android.graphics.Color;

/**
 * Created by Ffes on 2017/10/10.
 */

public class TitleDetail {
    String color;
    String title;

    public TitleDetail(){}

    public TitleDetail(String color, String title){
        this.color=color;
        this.title=title;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public String getTitle() {
        return title;
    }
}

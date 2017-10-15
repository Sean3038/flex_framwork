package com.example.ffes.flex_framwork.noteview.data;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ffes on 2017/8/28.
 */

public class KeyWord{
    String keyword;
    String color;
    RectF rect;

    public KeyWord(){}

    public KeyWord(String keyword,RectF rect,String color){
        this.keyword=keyword;
        this.rect=rect;
        this.color=color;
    }

    protected KeyWord(Parcel in) {
        keyword = in.readString();
        rect=in.readParcelable(Rect.class.getClassLoader());
        color=in.readString();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public Map<String,Object> toMap(){

        Map<String,Object> map=new HashMap<>();
        map.put("keyword",keyword);
        map.put("color",color);

        Map<String,Object> rectm=new HashMap<>();
        rectm.put("top",rect.top);
        rectm.put("left",rect.left);
        rectm.put("bottom",rect.bottom);
        rectm.put("right",rect.right);

        map.put("rect",rectm);
        return map;
    }
}

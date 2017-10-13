package com.example.ffes.flex_framwork.noteview.data;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ffes on 2017/8/28.
 */

//parcelable unimplement
public class KeyWord implements Parcelable {
    String keyword;
    int color;
    RectF rect;

    public KeyWord(){}

    public KeyWord(String keyword,RectF rect,int color){
        this.keyword=keyword;
        this.rect=rect;
        this.color=color;
    }

    protected KeyWord(Parcel in) {
        keyword = in.readString();
        rect=in.readParcelable(Rect.class.getClassLoader());
        color=in.readInt();
    }


    public static final Creator<KeyWord> CREATOR = new Creator<KeyWord>() {
        @Override
        public KeyWord createFromParcel(Parcel in) {
            return new KeyWord(in);
        }

        @Override
        public KeyWord[] newArray(int size) {
            return new KeyWord[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyword);
        dest.writeParcelable(rect, flags);
        dest.writeInt(color);
    }
}

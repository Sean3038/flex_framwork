package com.example.ffes.flex_framwork.noteview.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.ffes.flex_framwork.noteview.data.KeyWord;

/**
 * Created by Ffes on 2017/9/27.
 */

public class KeyFrame implements Frame {
    KeyWord keyWord;
    boolean isShow;
    boolean isEdit;

    public KeyWord getKeyWord() {
        return keyWord;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setKeyWord(KeyWord keyWord) {
        this.keyWord = keyWord;
    }

    public void draw(Canvas canvas){
        if(isShow) {
            Paint p = new Paint();
            p.setColor(keyWord.getColor());
            canvas.drawRect(keyWord.getRect(), p);
        }
    }

    @Override
    public RectF getRect() {
       return keyWord.getRect();
    }

    @Override
    public void setRect(RectF rect) {
        keyWord.setRect(rect);
    }
}

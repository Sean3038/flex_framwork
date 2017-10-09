package com.example.ffes.flex_framwork.noteview.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.ffes.flex_framwork.noteview.data.QA;

/**
 * Created by Ffes on 2017/9/27.
 */

public class QAFrame {
    QA qa;
    boolean isShow;
    boolean isEdit;
    boolean isOpen;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void setQa(QA qa) {
        this.qa = qa;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public QA getQa() {
        return qa;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public boolean isShow() {
        return isShow;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void draw(Canvas c){
        Paint p=new Paint();
        p.setColor(Color.BLUE);
        if(isOpen){
            p.setStyle(Paint.Style.STROKE);
            c.drawRect(qa.getRect(),p);
        }else{
            p.setStyle(Paint.Style.FILL_AND_STROKE);
            c.drawRect(qa.getRect(),p);
        }
    }
}

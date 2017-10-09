package com.example.ffes.flex_framwork.noteview.data;

import android.graphics.RectF;

/**
 * Created by Ffes on 2017/8/28.
 */

public class QA {
    int qaId;
    RectF rect;

    QA(){}

    QA(int qaId,RectF rect){
        this.qaId=qaId;
        this.rect=rect;
    }

    public int getQaId() {
        return qaId;
    }

    public RectF getRect() {
        return rect;
    }

    public void setQaId(int qaId) {
        this.qaId = qaId;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }
}

package com.example.ffes.flex_framwork.noteview.widget;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Ffes on 2017/9/27.
 */

public interface  Frame {
    boolean isEdit();
    void setEdit(boolean isEdit);
    RectF getRect();
    void setRect(RectF rect);
    void draw(Canvas canvas);
}

package com.example.ffes.flex_framwork.noteview.data;

import android.graphics.RectF;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ffes on 2017/8/28.
 */

public class QA {
    RectF rect;

    QA(){}

    QA(RectF rect){
        this.rect=rect;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();

        Map<String,Object> rectm=new HashMap<>();
        rectm.put("top",rect.top);
        rectm.put("left",rect.left);
        rectm.put("bottom",rect.bottom);
        rectm.put("right",rect.right);

        map.put("rect",rectm);



        return map;
    }
}

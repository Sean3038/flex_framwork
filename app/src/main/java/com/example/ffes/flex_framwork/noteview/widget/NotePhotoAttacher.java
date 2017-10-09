package com.example.ffes.flex_framwork.noteview.widget;

import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;


/**
 * Created by Ffes on 2017/9/21.
 */

public class NotePhotoAttacher extends PhotoViewAttacher {

    OnCustomEvent customEvent;


    public NotePhotoAttacher(ImageView imageView) {
        super(imageView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        if(customEvent!=null){
           if(customEvent.onTouch(v,ev)){
                return true;
           }
        }
        return super.onTouch(v,ev);
    }

    public void setCustomEvent(OnCustomEvent customEvent) {
        this.customEvent = customEvent;
    }
}

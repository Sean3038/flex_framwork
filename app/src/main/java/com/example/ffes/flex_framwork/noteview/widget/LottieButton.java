package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Ffes on 2017/8/27.
 */

public class LottieButton extends LottieAnimationView {

    boolean isOpened=false;
    Callback callback;
    public LottieButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LottieButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnCallbackListener(Callback callback){
        this.callback=callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    if (isOpened) {
                        close();
                    } else {
                        open();
                    }
                    break;
            }
        return true;
    }

    private void close(){
        isOpened=false;
        if(callback !=null){
            callback.onClose();
        }
        reverseAnimation();
    }

    private void open(){
        isOpened=true;
        if(callback !=null){
            callback.onOpen();
        }
        playAnimation(0.4f,0.5f);
    }

    public interface Callback{
        void onOpen();
        void onClose();
    }
}

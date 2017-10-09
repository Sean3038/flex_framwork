package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Ffes on 2017/8/27.
 */

public class SupplyButton extends android.support.v7.widget.AppCompatImageView{

    public SupplyButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SupplyButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface Callback{
        void onOpen();
        void onClose();
    }
}

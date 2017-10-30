package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by Ffes on 2017/10/30.
 */

public abstract class DepView extends FrameLayout{
    public DepView(@NonNull Context context) {
        super(context);
    }

    public abstract String getDep();
}

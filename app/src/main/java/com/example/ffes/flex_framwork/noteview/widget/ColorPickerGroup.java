package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/9/16.
 */

public class ColorPickerGroup extends LinearLayout implements ColorSelectBox.OnCheckedListener{

    List<ColorSelectBox> colorlist;
    private int currentColor;

    public ColorPickerGroup(Context context) {
        super(context);
        init();
    }

    public ColorPickerGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorPickerGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init(){
        colorlist=new ArrayList<>();
        if(getChildCount()==0){
            return;
        }
        for(int i=0;i<getChildCount();i++){
            if(getChildAt(i) instanceof ColorSelectBox){
                ColorSelectBox box=(ColorSelectBox)getChildAt(i);
                box.setOnCheckedListener(this);
                colorlist.add(box);
            }
        }
    }


    @Override
    public void addView(View child) {
        if(child instanceof ColorSelectBox){
            ColorSelectBox box=(ColorSelectBox)child;
            box.setOnCheckedListener(this);
            colorlist.add(box);
        }
        super.addView(child);
    }

    @Override
    public void onChecked(ColorSelectBox buttonView) {
        currentColor=buttonView.getColor();
        for(ColorSelectBox b:colorlist){
            if(!b.equals(buttonView)){
                b.setCheck(false);
            }
        }
    }


    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int color){
        for(ColorSelectBox b:colorlist){
            if(b.getColor()==color){
                b.setCheck(true);
                currentColor=color;
                return;
            }
        }
    }
}

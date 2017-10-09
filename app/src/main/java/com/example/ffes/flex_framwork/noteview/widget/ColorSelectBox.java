package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/9/16.
 */

public class ColorSelectBox extends View {

    private OnCheckedListener onCheckedListener;
    boolean isCheck;

    Paint color;
    ShapeDrawable circle;
    Drawable tick;

    public ColorSelectBox(Context context) {
        super(context);
        init();
    }

    public ColorSelectBox(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorSelectBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.ColorSelectBox,defStyleAttr,0);
        int color=typedArray.getColor(R.styleable.ColorSelectBox_Color,0xFFFF0000);
        this.color.setColor(color);
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    private void init(){
        this.color=new Paint();
        circle=new ShapeDrawable(new OvalShape());
        tick= ContextCompat.getDrawable(getContext(),R.drawable.checked);
        isCheck=false;
    }

    public int getColor(){
        return color.getColor();
    }

    public void setColor(int color){
        this.color.setColor(color);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                check();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void check() {
        setCheck(true);
        if(onCheckedListener!=null){
            onCheckedListener.onChecked(this);
        }
    }

    private void meansureSize(){
        int width=this.getWidth();
        int height=this.getHeight();
        circle.setBounds(0,0, width,height);
        tick.setBounds(width/4,height/4,width/4*3,height/4*3);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        meansureSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circle.getPaint().set(color);
        circle.draw(canvas);
        if(isCheck){
            tick.draw(canvas);
        }
    }

    public void setCheck(boolean check) {
        isCheck = check;
        invalidate();
    }

    interface OnCheckedListener{
        void onChecked(ColorSelectBox buttonView);
    }
}

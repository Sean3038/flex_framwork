package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 2017/8/10.
 */

public class Triangle extends View {
    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int color;

    public void setColor(int color) {
        this.color = color;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth() / 5;

        Path path = new Path();
        path.moveTo( w, 0);
        path.lineTo( 0 , 0);
        path.lineTo( 0 , w);
        path.lineTo( w , 0);
        path.close();
        Paint p = new Paint();
        p.setColor( color );
        canvas.drawPath(path, p);
    }
}

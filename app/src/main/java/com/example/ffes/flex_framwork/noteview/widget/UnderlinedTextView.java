package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/9/13.
 */

public class UnderlinedTextView extends AppCompatTextView {
    private Rect lineBoundsRect;
    private Paint underlinePaint;

    public UnderlinedTextView(Context context) {
        this(context, null, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderlinedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

    }

    private void init(Context context, AttributeSet attributeSet, int defStyle) {

        float density = context.getResources().getDisplayMetrics().density;

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlinedTextView, defStyle, 0);
        int mColor = typedArray.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);
        float mStrokeWidth = typedArray.getDimension(R.styleable.UnderlinedTextView_underlineWidth, density * 2);
        typedArray.recycle();

        lineBoundsRect = new Rect();
        underlinePaint = new Paint();
        underlinePaint.setStyle(Paint.Style.STROKE);
        underlinePaint.setStrokeCap(Paint.Cap.ROUND);
        underlinePaint.setColor(mColor); //color of the underline
        underlinePaint.setStrokeWidth(mStrokeWidth);
    }

    @ColorInt
    public int getUnderLineColor() {
        return underlinePaint.getColor();
    }

    public void setUnderLineColor(@ColorInt int mColor) {
        underlinePaint.setColor(mColor);
        invalidate();
    }

    public float getUnderlineWidth() {
        return underlinePaint.getStrokeWidth();
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        underlinePaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int count = getLineCount();

        final Layout layout = getLayout();
        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, lineBoundsRect);
            firstCharInLine = layout.getLineStart(i);
            lastCharInLine = layout.getLineEnd(i);

            x_start = layout.getPrimaryHorizontal(firstCharInLine);
            x_diff = layout.getPrimaryHorizontal(firstCharInLine + 1) - x_start;
            x_stop = layout.getPrimaryHorizontal(lastCharInLine - 1) + x_diff;

            canvas.drawLine(x_start, baseline + underlinePaint.getStrokeWidth(), x_stop, baseline + underlinePaint.getStrokeWidth(), underlinePaint);
        }

        super.onDraw(canvas);
    }
}

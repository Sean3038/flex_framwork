package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Ffes on 2017/9/11.
 */

public class NoteView extends CustomPhotoView implements OnCustomEvent {
    public static final int NONE=0;
    public static final int SELECTED=1;
    public static final int DRAG=2;
    public static final int RESIZE=3;

    public static final int KEYFRAME=0;
    public static final int QAFRAME=1;


    LinkedList<KeyFrame> keyrect;
    boolean isEdit =false;
    boolean isNewItem=false;
    int ViewerType=KEYFRAME;
    Frame edititem;

    int currentMode;

    float lastx;
    float lasty;

    Drawable cpd;
    RectF cp=new RectF();

    AddRemoveCallback callback;

    public NoteView(Context context) {
        this(context,null);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.NoteView);
        isEdit =typedArray.getBoolean(R.styleable.NoteView_isEdit,false);
    }


    private void initView() {
        keyrect = new LinkedList<>();
        cpd=ContextCompat.getDrawable(getContext(),R.drawable.expand);
        setCustomEvent(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(!isEdit) {
            invalidate();
            return false;
        }

        if(isNewItem){
            return newItem(event);
        }

        if(edititem!=null) {
            rectCP(edititem);
        }

        if(event.getPointerCount()>1){return false;}

        if(currentMode!=NONE && edititem!=null){
            return editItem(event);
        }else{
            return selectItem(event);
        }
    }


    public void show(String key){
        hideAll();
        for(KeyFrame item:keyrect){
            if(item.getKeyWord().getKeyword().equals(key)){
                if(isEdit){
                    edititem=item;
                    edititem.setEdit(true);
                    rectCP(edititem);
                }
                item.setShow(true);
            }
        }
        currentMode=SELECTED;
        invalidate();
    }

    public void hideAll(){
        if(isEdit){
            edititem=null;
        }
        for(KeyFrame item:keyrect){
            item.setShow(false);
        }
        invalidate();
    }

    private void cancelAllEdit(){
        for(KeyFrame k:keyrect){
            k.setEdit(false);
        }
    }

    public void add(String key,int color){
        if(isEdit){
            hideAll();
            KeyFrame keyFrame=new KeyFrame();
            KeyWord keyWord=new KeyWord(key,new RectF(),color);
            keyFrame.setKeyWord(keyWord);
            keyFrame.setShow(true);
            keyFrame.setEdit(true);
            keyrect.addLast(keyFrame);
            edititem=keyFrame;
            isNewItem=true;
            callback.preAdd();
        }
    }

    public void remove(String key){
        KeyFrame i=null;
        edititem=null;
        currentMode=NONE;
        for(KeyFrame item:keyrect){
            if(item.getKeyWord().getKeyword().equals(key)){
                i=item;
            }
        }
        if(i!=null){
            if(callback!=null) {
                callback.onRemoved(key);
            }
            keyrect.remove(i);
        }
        invalidate();
    }

    public List<KeyWord> getKeyWordList(){
        List<KeyWord> keyWordList=new ArrayList<>();
        for(KeyFrame item:keyrect){
            keyWordList.add(item.getKeyWord());
        }
        return keyWordList;
    }

    private boolean checkTouchOnRect(Frame item,float x,float y){
        Matrix matrix=new Matrix();
        getSuppMatrix(matrix);
        RectF rect=new RectF(item.getRect());
        matrix.mapRect(rect);
        return rect.contains(x,y);
    }

    private void drag(Frame item,float x,float y){
        float[] distance=caculateDistance(new float[]{x,y},new float[]{lastx,lasty});
        RectF rect=new RectF(item.getRect());
        RectF display=new RectF(getDisplayRect());
        Matrix matrix=new Matrix();
        getImageMatrix().invert(matrix);
        matrix.mapRect(display);
        rect.offset(distance[0], distance[1]);

        if(display.contains(rect)){
            item.getRect().set(rect);
            rectCP(item);
            return;
        }
        if(display.top>rect.top){rect.set(rect.left,display.top,rect.right,display.top+rect.height());}
        if(display.left>rect.left){rect.set(display.left,rect.top,display.left+rect.width(),rect.bottom);}
        if(display.right<rect.right){rect.set(display.right-rect.width(),rect.top,display.right,rect.bottom);}
        if(display.bottom<rect.bottom){rect.set(rect.left,display.bottom-rect.height(),rect.right,display.bottom);}
        item.getRect().set(rect);
        rectCP(item);
    }

    private void resize(Frame item,float x,float y){
        float[] distance=caculateDistance(new float[]{x,y},new float[]{lastx,lasty});
        RectF rect=new RectF(item.getRect());
        RectF display=new RectF(getDisplayRect());
        Matrix matrix=new Matrix();
        getImageMatrix().invert(matrix);
        matrix.mapRect(display);
        rect.set(rect.left,rect.top,rect.right+distance[0],rect.bottom+distance[1]);
        if(rect.width()<0){
            rect.set(rect.left,rect.top,rect.left,rect.bottom+distance[1]);
        }
        if(rect.height()<0){
            rect.set(rect.left,rect.top,rect.right+distance[0],rect.top);
        }
        if(display.contains(rect)){
            item.getRect().set(rect);
            rectCP(item);
            return;
        }
        if(display.top>rect.top){rect.set(rect.left,display.top,rect.right,rect.bottom);}
        if(display.left>rect.left){rect.set(display.left,rect.top,rect.right,rect.bottom);}
        if(display.right<rect.right){rect.set(rect.left,rect.top,display.right,rect.bottom);}
        if(display.bottom<rect.bottom){rect.set(rect.left,rect.top,rect.right,display.bottom);}
        item.setRect(rect);
    }

    private boolean newItem(MotionEvent event){
        float x=event.getX();
        float y=event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastx=x;
                lasty=y;
                if(checkInDisplay(x,y)){
                    if(callback !=null){
                        callback.onAdd();
                    }
                    float[] p=caculateActualPonit(x,y);
                    edititem.setRect(new RectF(p[0],p[1],p[0],p[1]));
                    currentMode=RESIZE;
                }
            case MotionEvent.ACTION_MOVE:
                if(currentMode==RESIZE) {
                    resize(edititem, x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentMode==RESIZE) {
                    isNewItem = false;
                    if(callback!=null){
                        callback.onAdded(((KeyFrame)edititem).getKeyWord());
                    }
                    currentMode=SELECTED;
                    resize(edititem, x, y);
                }
                break;
        }
        lastx=x;
        lasty=y;
        invalidate();
        return true;
    }

    private boolean selectItem(MotionEvent event){
        float x=event.getX();
        float y=event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(ViewerType==QAFRAME){

                }else if(ViewerType==KEYFRAME){
                    for(KeyFrame keyFrame:keyrect){
                        if(checkTouchOnRect(keyFrame,x,y) && keyFrame.isShow()){
                            cancelAllEdit();
                            edititem=keyFrame;
                            edititem.setEdit(true);
                            Log.d("TEST","SelectItem");
                            currentMode=SELECTED;
                            return true;
                        }
                    }
                }
        }
        return false;
    }

    private boolean editItem(MotionEvent event){
        float x=event.getX();
        float y=event.getY();
        Log.d("TEST","EditItem");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentMode=checkMode(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                switch(currentMode){
                    case NONE:
                        edititem=null;
                        return false;
                    case RESIZE:
                        resize(edititem,x,y);
                        break;
                    case DRAG:
                        drag(edititem,x,y);
                        break;
                    case SELECTED:
                        currentMode=checkMode(x,y);
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        lastx=x;
        lasty=y;
        invalidate();
        return true;
    }

    private boolean checkInDisplay(float x,float y){
        return getDisplayRect().contains(x,y);
    }

    private float[] caculateDistance(float[] cur,float[] last){
        float[] result=new float[2];
        result[0]=(cur[0]-last[0])/getScale();
        result[1]=(cur[1]-last[1])/getScale();
        return result;

    }

    private float[] caculateActualPonit(float x,float y){
        Matrix matrix=new Matrix();
        float[] p=new float[2];
        getImageMatrix().invert(matrix);
        matrix.mapPoints(p,new float[]{x,y});
        return p;
    }

    private int checkMode(float x,float y){
        float[] p=caculateActualPonit(x,y);
        rectCP(edititem);
       if(edititem.isEdit()){
           if(isOnCp(p[0],p[1])!=0){
               return RESIZE;
           }
       }
       if(checkTouchOnRect(edititem,p[0],p[1])){
           if(!edititem.isEdit()){
               return SELECTED;
           }
           return DRAG;
       }
       return NONE;
    }

    private int isOnCp(float x,float y){
        if(cp.contains(x,y)){
            return 1;
        }
        return 0;
    }

    private void rectCP(Frame item){
        RectF src=item.getRect();
        cp.set(0,0,150/getScale(),150/getScale());
        cp.offsetTo(src.right-cp.width()/2,src.bottom-cp.height()/2);
    }

    public void load(String noteUrl, final List<KeyWord> itemList, final OnLoadingNoteListener listener){
        if(listener!=null){
            listener.onLoad();
        }
        Picasso.with(getContext()).load(noteUrl).resize(1500,1500).centerInside().into(this, new Callback() {
            @Override
            public void onSuccess() {
               setKeyrect(itemList);
                if(listener!=null){
                    listener.onLoaded();
                }
            }

            @Override
            public void onError() {
                if(listener!=null){
                    listener.onError();
                }
            }
        });
    }

    public void setKeyrect(final List<KeyWord> itemList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                keyrect.clear();
                edititem=null;
                invalidate();
                for(KeyWord keyWord:itemList){
                    KeyFrame item=new KeyFrame();
                    item.setShow(false);
                    item.setEdit(false);
                    item.setKeyWord(keyWord);
                    keyrect.add(item);
                }
                invalidate();
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(keyrect==null){
            return;
        }
        canvas.save();
        canvas.concat(getImageMatrix());
        if(edititem!=null){
            edititem.draw(canvas);
            if(currentMode!=NONE && isNewItem==false){
                drawCp(canvas);
            }
        }
        canvas.restore();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        invalidate();
    }

    private void drawCp(Canvas canvas){
        cpd.setBounds((int)cp.left,(int)cp.top,(int) cp.right,(int)cp.bottom);
        cpd.draw(canvas);
    }

    public void setAddNoteListener(AddRemoveCallback listener){
        callback=listener;
    }

    public boolean isNewItem() {
        return isNewItem;
    }

    public interface OnLoadingNoteListener{
        void onLoad();
        void onLoaded();
        void onError();
    }

    public interface AddRemoveCallback {
        void preAdd();
        void onAdd();
        void onAdded(KeyWord keyword);
        void onRemoved(String key);
    }
}

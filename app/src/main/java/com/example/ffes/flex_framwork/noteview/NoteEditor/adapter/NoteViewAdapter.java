package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.OnImageClick;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.squareup.picasso.Picasso;

/**
 * Created by Ffes on 2017/10/2.
 */

public class NoteViewAdapter extends PagerAdapter implements PageDataModel{

    Context context;
    OnImageClick listener;
    PageModel model;
    public  NoteViewAdapter(Context context,OnImageClick listener){
        this.context=context;
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return model.getTotalPage();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=(View)object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView=new ImageView(context);
        final Page page=model.getPage(position);
        loadImage(imageView,page.getimageurl());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickImage(position);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    private void loadImage(ImageView imageView,String pageurl){
        Picasso.with(context)
                .load(pageurl)
                .resize(1500,1500)
                .centerInside()
                .into(imageView);
    }

    @Override
    public void notifyAddPage() {
        notifyDataSetChanged();
    }

    @Override
    public void notifyRemovePage(int index) {
        notifyDataSetChanged();
    }

    @Override
    public void notifyCurrentPage(int page) {

    }

    @Override
    public void bind(PageModel pageStateModel) {
        model=pageStateModel;
    }

    @Override
    public void unbind() {
        model=null;
    }
}

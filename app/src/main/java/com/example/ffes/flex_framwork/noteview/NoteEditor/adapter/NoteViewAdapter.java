package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.noteview.NoteEditor.view.OnImageClick;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/2.
 */

public class NoteViewAdapter extends PagerAdapter implements PageDataModel{

    Context context;
    List<String> pages;
    OnImageClick listener;

    public  NoteViewAdapter(Context context,OnImageClick listener){
        this.context=context;
        this.pages=new ArrayList<>();
        this.listener=listener;
    }

    public void setPageList(List<String> pages){
        this.pages.clear();
        this.pages.addAll(pages);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=(View)object;
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView=new ImageView(context);
        final String pageurl=pages.get(position);
        loadImage(imageView,pageurl);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickImage(pages.indexOf(pageurl));
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void add(String pageurl) {
        pages.add(pageurl);
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position){
        pages.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void setCurrentPage(int page) {

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

}

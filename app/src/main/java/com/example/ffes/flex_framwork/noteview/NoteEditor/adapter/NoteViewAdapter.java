package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.noteview.NoteEditor.view.OnImageClick;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/2.
 */

public class NoteViewAdapter extends PagerAdapter{

    Context context;
    List<PageContent> pages;
    OnImageClick listener;

    public  NoteViewAdapter(Context context,OnImageClick listener){
        this.context=context;
        this.pages=new ArrayList<>();
        this.listener=listener;
    }

    public void setPageList(List<PageContent> pages){
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
        final PageContent pageContent=pages.get(position);
        loadImage(imageView,pageContent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {listener.onClickImage(pageContent.getPage());
            }
        });
        container.addView(imageView);
        return imageView;
    }

    public void remove(int position){
        pages.remove(position);
        notifyDataSetChanged();
    }

    public PageContent get(int position){
        return pages.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    private void loadImage(ImageView imageView,PageContent pageContent){
        Picasso.with(context)
                .load(pageContent.getPageNoteUrl())
                .resize(1500,1500)
                .centerInside()
                .into(imageView);
    }

}

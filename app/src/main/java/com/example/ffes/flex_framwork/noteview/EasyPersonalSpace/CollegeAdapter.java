package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ffes on 2017/10/30.
 */

public class CollegeAdapter extends PagerAdapter {

    List<DepView> views;

    public CollegeAdapter(List<DepView> views){
        this.views=views;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}

package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.SupplyFragment;
import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/8/29.
 */

public class SupplyPageAdapter extends FragmentStatePagerAdapter{

    List<Integer> supplyList;
    String noteUrl;
    SupplyFragment supplyFragment;

    public SupplyPageAdapter(FragmentManager fm) {
        super(fm);
        supplyList=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return SupplyFragment.newInstance(null,false);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if(supplyFragment!=object){
            supplyFragment=(SupplyFragment)object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return supplyList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void setSupplyData(String noteUrl,List<Integer> page){
        this.noteUrl=noteUrl;
        this.supplyList.clear();
        this.supplyList.addAll(page);
        notifyDataSetChanged();
    }

    public SupplyFragment getCurrentFragment(){
        return supplyFragment;
    }

}

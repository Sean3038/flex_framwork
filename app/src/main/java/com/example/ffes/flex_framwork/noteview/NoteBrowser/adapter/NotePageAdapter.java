package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/9/12.
 */

public class NotePageAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener{

    List<Integer> pagelist;
    String noteUrl;
    boolean isEdit;


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    NoteFragment currentfragment;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
       currentfragment=(NoteFragment) object;
    }

    public NotePageAdapter(FragmentManager fm) {
        super(fm);
        pagelist=new ArrayList<>();
        isEdit=false;
    }

    public void setNoteList(List<Integer> list){
        pagelist.clear();
        for(Integer i:list){
            pagelist.add(i);
        }
        notifyDataSetChanged();
    }

    public void setEdit(boolean isEdit){
        this.isEdit=isEdit;
        notifyDataSetChanged();
    }

    @Override
    public NoteFragment getItem(int position) {
        return NoteFragment.newInstance(noteUrl,pagelist.get(position),isEdit);
    }

    @Override
    public int getCount() {
        return pagelist.size();
    }

    public void addKey(String key){
        if(key!=null) {
            currentfragment.addKeyWord(key);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentfragment.reload();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

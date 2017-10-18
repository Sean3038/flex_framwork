package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyWordStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.QAStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/9/12.
 */

public class NotePageAdapter extends FragmentStatePagerAdapter implements PageDataModel{

    List<Integer> pagelist;
    PageModel stateModel;
    boolean isEdit;


    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

    }

    public NotePageAdapter(FragmentManager fm) {
        super(fm);
        pagelist=new ArrayList<>();
        isEdit=false;
    }

    @Override
    public NoteFragment getItem(int position) {
        return NoteFragment.newInstance(stateModel.getPage(position).getimageurl(),new KeyWordStateModel(stateModel.getPage(position).getkeywordlist()),new QAStateModel(stateModel.getPage(position).getqalist()),isEdit);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return stateModel.getTotalPage();
    }

    @Override
    public void bind(PageModel stateModel) {
        this.stateModel=stateModel;
    }

    @Override
    public void unbind() {
        stateModel=null;
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
}

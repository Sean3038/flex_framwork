package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.SupplyFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.List;


/**
 * Created by Ffes on 2017/10/17.
 */

public class SupplyWindow {

    LinearLayout root;
    boolean isOpened;

    public SupplyWindow(ViewGroup view){
        isOpened=false;
        root=(LinearLayout)view.findViewById(R.id.supplylayout);
    }

    public void showSuupplyWindow(List<Supply> supplies,FragmentManager fm){
        isOpened=true;
        root.setVisibility(View.VISIBLE);
        SupplyFragment supplyFragment= SupplyFragment.newInstance(new SupplyStateModel(supplies),true);
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.supply,supplyFragment,"SupplyContent");
        ft.commit();
    }

    public void hideSupplyWindow(){
        isOpened=false;
        root.setVisibility(View.GONE);
    }

    public boolean isOpened() {
        return isOpened;
    }
}

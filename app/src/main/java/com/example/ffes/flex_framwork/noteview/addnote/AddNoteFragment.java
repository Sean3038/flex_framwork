package com.example.ffes.flex_framwork.noteview.addnote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.R;


/**
 * Created by Ffes on 2017/11/14.
 */

public class AddNoteFragment extends Fragment {
    public static AddNoteFragment newInstance(){
        AddNoteFragment fragment=new AddNoteFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.addnote_layout,container,false);
    }
}

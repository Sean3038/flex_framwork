package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.LinkNoteAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.LinkNote;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/23.
 */

public class LinkMenuDialog extends DialogFragment{

    public static final String URL_KEY="noteurl";

    RecyclerView menu;
    Button confirm;
    Button cancel;

    FrameLayout progress;

    NoteRepository noteRepository;
    LinkNoteAdapter adapter;

    GetLinkNote listener;
    public static LinkMenuDialog newInstance(String noteurl,GetLinkNote listener){
        LinkMenuDialog fragment=new LinkMenuDialog();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteurl);
        fragment.setArguments(bundle);
        fragment.setGetLinkNoteListener(listener);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menu=(RecyclerView)view.findViewById(R.id.menu);
        confirm=(Button)view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCallBack(adapter.getSelectedNote());
                dismiss();
            }
        });
        cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        progress=(FrameLayout)view.findViewById(R.id.progress);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        //從這開始找所有筆記的關鍵字
        getAllNote();
    }

    public void getAllNote(){
        final Context context=this.getContext();
        showprogress();
        noteRepository.getAllNote(getArguments().getString(URL_KEY), new OnGetDataCallBack<List<LinkNote>>() {

            @Override
            public void onSuccess(List<LinkNote> data) {
                adapter=new LinkNoteAdapter(context,data);
                menu.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                menu.setAdapter(adapter);
                closeprogress();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void showprogress(){
        progress.setVisibility(View.VISIBLE);
    }

    public void closeprogress(){
        progress.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.link_menu_fragment,container);
    }

    public void setGetLinkNoteListener(GetLinkNote listener){
        this.listener=listener;
    }

    interface GetLinkNote{
        void onCallBack(Map<String,List<String>> notelist);
    }
}

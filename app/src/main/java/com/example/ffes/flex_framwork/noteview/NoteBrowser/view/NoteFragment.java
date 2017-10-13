package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyWordAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyWordStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.QAStateModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.widget.NoteView;

import java.util.Random;

/**
 * Created by Ffes on 2017/9/1.
 */

public class NoteFragment extends Fragment implements KeyWordAdapter.OnKeyClick,KeyWordAdapter.OnDeleteKeyClick,NoteView.AddRemoveCallback{

    public static final String EDIT_STATE_KEY="IsEdit";

    NoteView noteView;
    RecyclerView listview;
    ProgressBar progressBar;
    FrameLayout addkeynotify;

    KeyWordAdapter keyWordAdapter;

    KeyWordStateModel stateModel;
    QAStateModel qaStateModel;

    public static NoteFragment newInstance(KeyWordStateModel keyWordStateModel, QAStateModel qaStateModel, boolean isEdit){
        NoteFragment fragment=new NoteFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean(EDIT_STATE_KEY,isEdit);
        fragment.setArguments(bundle);
        fragment.setQaStateModel(qaStateModel);
        fragment.setKeyWordStateModel(keyWordStateModel);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_fragment,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteView=(NoteView)view.findViewById(R.id.noteview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar2);
        listview=(RecyclerView)view.findViewById(R.id.keywordlist);
        addkeynotify=(FrameLayout)view.findViewById(R.id.addkeynotify);
        keyWordAdapter=new KeyWordAdapter();
        listview.setAdapter(keyWordAdapter);
        listview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        setEdit(getArguments().getBoolean(EDIT_STATE_KEY));
        keyWordAdapter.setDeleteKeyClick(this);
        keyWordAdapter.setKeyClick(this);
        noteView.setAddNoteListener(this);

        addkeynotify.setVisibility(View.GONE);
    }

    public void setEdit(boolean isEdit){
        noteView.setEdit(isEdit);
        keyWordAdapter.setEdit(isEdit);
    }

    public void addKeyWord(String key){
        if(!noteView.isNewItem()) {
            Random random = new Random();
            int color = Color.argb(100, random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100);
            KeyWord keyWord=new KeyWord(key,null,color);
            stateModel.addKeyWord(keyWord);
//            noteView.add(key, color);
//            keyWordAdapter.add(key, color);
        }
    }

    @Override
    public void onKeyClick(String key) {
        noteView.show(key);
    }

    public void showAddKeyNotify() {
        addkeynotify.setVisibility(View.VISIBLE);
    }

    public void hideAddKeyNotify() {
        addkeynotify.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteKeyClick(String key) {
        stateModel.removeKeyWord(key);
    }

    @Override
    public void preAdd() {
        showAddKeyNotify();
    }

    @Override
    public void onAdd() {
        hideAddKeyNotify();
    }

    @Override
    public void onAdded(KeyWord keyword) {
        stateModel.addKeyWord(keyword);
    }

    @Override
    public void onRemoved(String key) {
        stateModel.removeKeyWord(key);
    }

    public void setQaStateModel(QAStateModel qaStateModel) {
        this.qaStateModel = qaStateModel;
    }

    public void setKeyWordStateModel(KeyWordStateModel stateModel) {
        this.stateModel = stateModel;
    }
}

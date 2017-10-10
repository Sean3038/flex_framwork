package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyWordAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteShowContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageContentModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.NoteShowPresenter;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.widget.NoteView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

/**
 * Created by Ffes on 2017/9/1.
 */

public class NoteFragment extends Fragment implements NoteShowContract.View, KeyWordAdapter.OnKeyClick,KeyWordAdapter.OnDeleteKeyClick,NoteView.AddRemoveCallback{

    public static final String URL_KEY="NoteURL";
    public static final String PAGE_KEY="Page";
    public static final String EDIT_STATE_KEY="IsEdit";

    NoteView noteView;
    RecyclerView listview;
    ProgressBar progressBar;
    FrameLayout addkeynotify;

    KeyWordAdapter keyWordAdapter;

    NoteShowContract.Presenter presenter;

    public static NoteFragment newInstance(String noteURL,int page,boolean isEdit){
        NoteFragment fragment=new NoteFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteURL);
        bundle.putInt(PAGE_KEY,page);
        bundle.putBoolean(EDIT_STATE_KEY,isEdit);
        fragment.setArguments(bundle);
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
        presenter=new NoteShowPresenter(this,new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()));

        addkeynotify.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
                presenter.LoadData(getArguments().getString(URL_KEY),getArguments().getInt(PAGE_KEY));
    }

    @Override
    public void onPause() {
        presenter.saveAllKeyWord((int)getArguments().get(PAGE_KEY),noteView.getKeyWordList());
        super.onPause();
    }

    public void setEdit(boolean isEdit){
        noteView.setEdit(isEdit);
        keyWordAdapter.setEdit(isEdit);
    }

    public void addKeyWord(String key){
        if(!noteView.isNewItem()) {
            Random random = new Random();
            int color = Color.argb(100, random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100);
            noteView.add(key, color);
            keyWordAdapter.add(key, color);
        }
    }

    @Override
    public void onKeyClick(String key) {
        noteView.show(key);
    }

    @Override
    public void setKeyData(String noteImageUrl,List<KeyWord> keyData) {
        noteView.load(noteImageUrl, keyData, new NoteView.OnLoadingNoteListener() {
            @Override
            public void onLoad() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoaded() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        keyWordAdapter.setKeylist(keyData);
    }

    @Override
    public void showAddKeyNotify() {
        addkeynotify.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAddKeyNotify() {
        addkeynotify.setVisibility(View.GONE);
    }

    @Override
    public void onDeleteKeyClick(String key) {
        keyWordAdapter.remove(key);
        noteView.remove(key);
    }

    public void reload(){
        presenter.LoadData(getArguments().getString(URL_KEY),getArguments().getInt(PAGE_KEY));
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
        presenter.addKeyWordFrame(getArguments().getInt(PAGE_KEY),keyword);
    }

    @Override
    public void onRemoved(String key) {
        presenter.removeKeyWordFrame(getArguments().getInt(PAGE_KEY),key);
    }
}

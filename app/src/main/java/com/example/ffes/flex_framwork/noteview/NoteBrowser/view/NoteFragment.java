package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyWordAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyWordStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.QAStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.AddKeyDialogFragment;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.widget.NoteView;

import java.util.Random;

/**
 * Created by Ffes on 2017/9/1.
 */

public class NoteFragment extends Fragment implements KeyWordAdapter.OnKeyClick,KeyWordAdapter.OnDeleteKeyClick,NoteView.AddRemoveCallback{

    public static final String PHOTO_KEY="photokey";
    public static final String EDIT_STATE_KEY="IsEdit";

    NoteView noteView;
    RecyclerView listview;
    ProgressBar progressBar;
    FrameLayout addkeynotify;

    ImageView save_btn;
    ImageView add_btn;

    KeyWordAdapter keyWordAdapter;

    KeyWordStateModel stateModel;
    QAStateModel qaStateModel;

    public static NoteFragment newInstance(String imageurl,KeyWordStateModel keyWordStateModel, QAStateModel qaStateModel, boolean isEdit){
        NoteFragment fragment=new NoteFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean(EDIT_STATE_KEY,isEdit);
        bundle.putString(PHOTO_KEY,imageurl);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().getBoolean(EDIT_STATE_KEY)){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.editor_detail_toolbar);
            save_btn=(ImageView) getActivity().findViewById(R.id.save_btn);
            add_btn=(ImageView) getActivity().findViewById(R.id.add_btn);
            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddKeyDialog();
                }
            });
            add_btn.setAnimation(null);
            add_btn.clearAnimation();
            add_btn.setVisibility(View.VISIBLE);
        }
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
        noteView.load(getArguments().getString(PHOTO_KEY), new NoteView.OnLoadingNoteListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onLoaded() {

            }

            @Override
            public void onError() {

            }
        });
        stateModel.addModel(keyWordAdapter);
        stateModel.addModel(noteView);
    }

    public void setEdit(boolean isEdit){
        noteView.setEdit(isEdit);
        keyWordAdapter.setEdit(isEdit);
    }

    public void addKeyWord(String key){
        if(!noteView.isNewItem()) {
            Random random = new Random();
            int color = Color.argb(100, random.nextInt(100) + 100, random.nextInt(100) + 100, random.nextInt(100) + 100);
            KeyWord keyWord=new KeyWord(key,new RectF(0,0,0,0),String.format("#%06X", (0xFFFFFF & color)));
            stateModel.addKeyWord(keyWord);
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
    public void onDeleteKeyClick(int index) {
        stateModel.removeKeyWord(index);
    }

    @Override
    public void preAdd() {
        showAddKeyNotify();
        listview.setVisibility(View.GONE);
        add_btn.setVisibility(View.GONE);
        save_btn.setVisibility(View.GONE);
    }

    @Override
    public void onAdd() {
        hideAddKeyNotify();
    }

    @Override
    public void onAdded(KeyWord keyword) {
        listview.setVisibility(View.VISIBLE);
        add_btn.setVisibility(View.VISIBLE);
        save_btn.setVisibility(View.VISIBLE);
    }

    public void setQaStateModel(QAStateModel qaStateModel) {
        this.qaStateModel = qaStateModel;
    }

    public void setKeyWordStateModel(KeyWordStateModel stateModel) {
        this.stateModel = stateModel;
    }

    public void showAddKeyDialog() {
        AddKeyDialogFragment dialogFragment=AddKeyDialogFragment.newInstance(new AddKeyDialogFragment.OnConfirmListener() {
            @Override
            public void onConfirm(String key) {
                if(!key.equals("")) {
                   addKeyWord(key);
                }
            }
        });
        dialogFragment.show(getFragmentManager(),"AddKeyDialogFragment");
    }
}

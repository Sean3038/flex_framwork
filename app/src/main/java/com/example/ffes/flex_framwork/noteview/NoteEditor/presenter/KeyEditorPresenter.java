package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteEditor.KeyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyEditorModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnGetDataCallBack;

import java.util.List;

/**
 * Created by Ffes on 2017/9/25.
 */

public class KeyEditorPresenter implements KeyEditorContract.Presenter {

    KeyEditorContract.View view;
    KeyEditorModel model;

    public KeyEditorPresenter(KeyEditorContract.View view, KeyEditorModel model){
        this.view=view;
        this.model=model;
    }

    @Override
    public void loadData(String noteUrl) {
        model.getShowPages(noteUrl, new OnGetDataCallBack<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> pages) {
                view.setNoteList(pages);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    @Override
    public void clickAdd() {
        view.showAddKeyDialog();
    }
}

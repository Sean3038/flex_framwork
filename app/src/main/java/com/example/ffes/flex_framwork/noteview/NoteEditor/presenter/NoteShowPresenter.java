package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteShowContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageContentModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.List;

/**
 * Created by Ffes on 2017/9/25.
 */

public class NoteShowPresenter implements NoteShowContract.Presenter{

    NoteShowContract.View view;
    PageContentModel model;

    public NoteShowPresenter(NoteShowContract.View view,PageContentModel model){
        this.view=view;
        this.model=model;
    }

    @Override
    public void LoadData(String noteUrl, final int page) {

        model.getPageContent(noteUrl, page, new OnGetDataCallBack<Page>() {
            @Override
            public void onSuccess(Page page) {
                view.setKeyData(page.getImageurl(),page.getKeyWordList());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void saveAllKeyWord(int page, List<KeyWord> keyWords) {

    }

    @Override
    public void addKeyWordFrame(int page, KeyWord keyWord) {
    }

    @Override
    public void removeKeyWordFrame(int page, String key) {

    }
}

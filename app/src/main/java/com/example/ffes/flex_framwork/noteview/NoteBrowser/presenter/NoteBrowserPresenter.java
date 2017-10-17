package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteBrowserModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

import java.util.List;



/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteBrowserPresenter implements NoteBrowserContract.Presenter{

    NoteBrowserContract.View view;
    NoteBrowserModel model;
    PageModel pageModel;

    public NoteBrowserPresenter(NoteBrowserContract.View view, PageModel pageModel, NoteBrowserModel model){
        this.view=view;
        this.model=model;
        this.pageModel=pageModel;
    }

    @Override
    public void loadNote(final String noteUrl) {

        model.getPages(noteUrl, new OnGetDataCallBack<Page>() {
            @Override
            public void onSuccess(Page data) {
                pageModel.addPage(data);
                model.getNoteName(noteUrl, new OnUpLoadDataCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        view.setTitle(s);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }

            @Override
            public void onFailure() {

            }
        });
    }
}

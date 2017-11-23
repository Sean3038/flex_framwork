package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteBrowserModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

import java.util.List;



/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteBrowserPresenter implements NoteBrowserContract.Presenter{

    NoteBrowserContract.View view;
    NoteRepository model;
    PageModel pageModel;
    AuthRepository authRepository;
    public NoteBrowserPresenter(NoteBrowserContract.View view, PageModel pageModel, NoteRepository model, AuthRepository authRepository){
        this.view=view;
        this.model=model;
        this.pageModel=pageModel;
        this.authRepository=authRepository;
    }

    @Override
    public void loadNote(final String uid,final String noteUrl) {
        view.showMessageProgress("讀取筆記資料");
        final int[] c = {0};
        model.getPages(uid,noteUrl, new OnGetDataCallBack<List<Page>>() {
            @Override
            public void onSuccess(List<Page> data) {
                pageModel.addPage(data);
                c[0]++;
                if(c[0] ==2){
                    view.closeMessageProgress();
                }
            }
            @Override
            public void onFailure() {

            }
        });
        model.getNoteName(uid,noteUrl, new OnUpLoadDataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                view.setTitle(s);
                c[0]++;
                if(c[0] ==2){
                    view.closeMessageProgress();
                }
            }
            @Override
            public void onFailure() {

            }
        });
    }
}

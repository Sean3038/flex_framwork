package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageStateModel;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

/**
 * Created by Ffes on 2017/9/18.
 */

public class NoteEidtorPresenter implements NoteEditorContract.Presenter{

    NoteEditorContract.View view;
    NoteLoadModel model;
    PageStateModel stateModel;

    public NoteEidtorPresenter(NoteEditorContract.View view, NoteLoadModel model,PageStateModel stateModel){
        this.view=view;
        this.model=model;
        this.stateModel=stateModel;
    }

    @Override
    public void loadData(String noteUrl) {
        //setpagestate;

        model.getPages(noteUrl, new OnGetDataCallBack<Page>() {
            @Override
            public void onSuccess(Page data) {
                stateModel.add(data);
            }

            @Override
            public void onFailure() {

            }
        });

        model.getTitleDetail(noteUrl, new OnGetDataCallBack<TitleDetail>() {
            @Override
            public void onSuccess(TitleDetail data) {
                view.setTitleDetail(data.getTitle(),data.getColor());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void removePage(String noteUrl, int page) {

    }


    @Override
    public void addPage(String noteUrl, int page) {

    }

    @Override
    public void updataTitleDetail(String noteurl, String string, int color) {
    }

    @Override
    public void getTitleDetail(String noteUrl) {
        model.getTitleDetail(noteUrl, new OnGetDataCallBack<TitleDetail>() {
            @Override
            public void onSuccess(TitleDetail data) {
                view.showTitleEditor(data.getTitle(),data.getColor());
            }

            @Override
            public void onFailure() {

            }
        });
    }

}

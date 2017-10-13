package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.TitleDetailStateModel;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

/**
 * Created by Ffes on 2017/9/18.
 */

public class NoteEidtorPresenter implements NoteEditorContract.Presenter{

    NoteEditorContract.View view;
    NoteLoadModel model;
    PageStateModel stateModel;
    TitleDetailStateModel titleDetailStateModel;

    public NoteEidtorPresenter(NoteEditorContract.View view, NoteLoadModel model, PageStateModel stateModel, TitleDetailStateModel titleDetailStateModel){
        this.view=view;
        this.model=model;
        this.stateModel=stateModel;
        this.titleDetailStateModel=titleDetailStateModel;
    }

    @Override
    public void loadData(final String noteUrl) {
        view.showprogress("讀取頁面");
        model.getPages(noteUrl, new OnGetDataCallBack<Page>() {
            @Override
            public void onSuccess(Page data) {
                stateModel.addPage(data);
                view.closeprogress();

                view.showprogress("讀取標題資訊");
                model.getTitleDetail(noteUrl, new OnGetDataCallBack<TitleDetail>() {
                    @Override
                    public void onSuccess(TitleDetail data) {
                        titleDetailStateModel.setTitleDetail(data);
                        view.closeprogress();
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

    @Override
    public void updateTitleDetail(String noteurl, String title, String color) {
        TitleDetail titleDetail=new TitleDetail(color,title);
        titleDetailStateModel.setTitleDetail(titleDetail);
    }

    @Override
    public void getTitleDetail(String noteUrl) {
        view.showTitleEditor(titleDetailStateModel.getTitleDetail().getTitle(),titleDetailStateModel.getTitleDetail().getColor());
    }

}

package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnUpLoadDataCallback;
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
        model.getPages(noteUrl, new OnGetDataCallBack<Page>() {
            @Override
            public void onSuccess(Page data) {
                Log.d("Page",data.getImageurl());
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
    public void updateTitleDetail(String noteurl, String title, String color) {
        model.updateTitleDetial(noteurl, title, color, new OnUpLoadDataCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
        view.setTitleDetail(title,color);
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

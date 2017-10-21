package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.TitleDetailModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/9/18.
 */

public class NoteEidtorPresenter implements NoteEditorContract.Presenter{

    NoteEditorContract.View view;
    NoteLoadModel model;
    ImageRepository imageRepository;
    PageModel stateModel;
    TitleDetailModel titleDetailStateModel;

    public NoteEidtorPresenter(NoteEditorContract.View view, NoteLoadModel model,ImageRepository imageRepository, PageModel stateModel, TitleDetailModel titleDetailStateModel){
        this.view=view;
        this.model=model;
        this.stateModel=stateModel;
        this.imageRepository=imageRepository;
        this.titleDetailStateModel=titleDetailStateModel;
    }

    @Override
    public void loadData(final String noteUrl) {
        view.showprogress("讀取頁面");
        model.getPages(noteUrl, new OnGetDataCallBack<List<Page>>() {

            @Override
            public void onSuccess(List<Page> data) {
                stateModel.addPage(data);
                view.closeprogress();
                view.showprogress("讀取標題資訊");
                model.getTitleDetail(noteUrl, new OnGetDataCallBack<TitleDetail>() {
                    @Override
                    public void onSuccess(TitleDetail data) {
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

    @Override
    public void addPage(final String noteUrl, String photurl) {
        imageRepository.addPagePhoto(noteUrl, photurl, new OnUpLoadDataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Page p=new Page();
                p.setqalist(new ArrayList<QA>());
                p.setsupplylist(new ArrayList<Supply>());
                p.setkeywordlist(new HashMap<String, KeyWord>());
                p.setimageurl(s);
                stateModel.addPage(p);
                model.addPage(noteUrl, p, new OnUpLoadDataCallback<String>() {

                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                view.hidenotify();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void saveNote(final String noteUrl) {
        view.showprogress("儲存筆記");
        final Map<String,Object> map=stateModel.toMap();

        model.updateNoteContent(noteUrl, map, new OnUpLoadDataCallback() {
            @Override
            public void onSuccess(Object o) {
                model.updateTitleDetial(noteUrl, titleDetailStateModel.getTitleDetail(), new OnUpLoadDataCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        view.closeprogress();
                        view.end();
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

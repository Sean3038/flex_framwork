package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.TitleDetailModel;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
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
    NoteRepository model;
    ImageRepository imageRepository;
    AuthRepository authRepository;
    PageModel stateModel;
    TitleDetailModel titleDetailStateModel;

    public NoteEidtorPresenter(NoteEditorContract.View view, NoteRepository model, ImageRepository imageRepository, AuthRepository authRepository, PageModel stateModel, TitleDetailModel titleDetailStateModel){
        this.view=view;
        this.model=model;
        this.stateModel=stateModel;
        this.imageRepository=imageRepository;
        this.authRepository=authRepository;
        this.titleDetailStateModel=titleDetailStateModel;
    }

    @Override
    public void loadData(final String noteUrl) {
        view.showprogress("讀取頁面");
        model.getPages(authRepository.getCurrentId(),noteUrl, new OnGetDataCallBack<List<Page>>() {

            @Override
            public void onSuccess(List<Page> data) {
                stateModel.addPage(data);
                if(data.size()==0){
                    view.shownotify();
                }else{
                    view.hidenotify();
                }
                view.closeprogress();
                view.showprogress("讀取標題資訊");
                model.getTitleDetail(authRepository.getCurrentId(),noteUrl, new OnGetDataCallBack<TitleDetail>() {
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
    public void addNote() {
        view.showprogress("新增筆記");
        view.shownotify();
        titleDetailStateModel.setTitleDetail(new TitleDetail("#f6b9b9","Title"));
        model.addNote(authRepository.getCurrentId(), new OnUpLoadDataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                view.setNoteId(s);
                view.closeprogress();
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
                model.addPage(authRepository.getCurrentId(),noteUrl, p, new OnUpLoadDataCallback<String>() {

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
    public void addLinkPage(String noteUrl, final Map<String, List<String>> notelist) {
        view.showprogress("連結筆記");
        final int[] c = {0};
        if(notelist.size()==0){
            view.closeprogress();
        }
        for(String key:notelist.keySet()){
            model.getLinkPageByKeyWord(key, notelist.get(key), new OnGetDataCallBack<List<Page>>() {
                @Override
                public void onSuccess(List<Page> data) {
                    stateModel.addPage(data);
                    c[0]++;
                    if(c[0] ==notelist.keySet().size()){
                        view.closeprogress();
                    }
                    view.hidenotify();
                }

                @Override
                public void onFailure() {
                    view.closeprogress();
                }
            });
        }
    }

    @Override
    public void saveNote(final String noteUrl) {
        view.showprogress("儲存筆記");
        final Map<String,Object> map=stateModel.toMap();
        model.updateNoteContent(authRepository.getCurrentId(),noteUrl, (Map<String, Object>) map.get("page"), new OnUpLoadDataCallback() {
            @Override
            public void onSuccess(Object o) {
                model.updateKeyWord(authRepository.getCurrentId(),noteUrl, (Map<String, Object>) map.get("key"));
                model.updateTitleDetial(authRepository.getCurrentId(),noteUrl, titleDetailStateModel.getTitleDetail(), new OnUpLoadDataCallback() {
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

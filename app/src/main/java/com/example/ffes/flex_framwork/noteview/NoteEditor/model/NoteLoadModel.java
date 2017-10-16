package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

import java.util.List;
import java.util.Map;


/**
 * Created by Ffes on 2017/10/10.
 */

public interface NoteLoadModel {
    void getPages(String url,OnGetDataCallBack<Page> callBack);
    void getTitleDetail(String url, OnGetDataCallBack<TitleDetail> callBack);
    void updateTitleDetial(String url,TitleDetail titleDetail,OnUpLoadDataCallback callback);
    void addPage(String url,Page page,OnUpLoadDataCallback callback);
    void updateNoteContent(String url, Map<String,Object> p, TitleDetail titleDetail, OnUpLoadDataCallback callback);
    void updatePageLink(String url,Map<String,Object> link,OnUpLoadDataCallback callback);
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;


/**
 * Created by Ffes on 2017/10/10.
 */

public interface NoteLoadModel {
    void getPages(String url,OnGetDataCallBack<Page> callBack);
    void getTitleDetail(String url, OnGetDataCallBack<TitleDetail> callBack);
    void updateTitleDetial(String url,String title,String color,OnUpLoadDataCallback callback);
    void addPage(String url,Page page,OnUpLoadDataCallback callback);
}

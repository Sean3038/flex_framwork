package com.example.ffes.flex_framwork.noteview.NoteEditor.model;


import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.List;

/**
 * Created by Ffes on 2017/10/16.
 */

public interface NoteBrowserModel {
    void getPages(final String url, final OnGetDataCallBack<Page> callBack);
    void getNoteName(String noteurl, OnUpLoadDataCallback<String> callBack);
    void getKeyList(String url,OnGetDataCallBack<List<String>> callBack);

}

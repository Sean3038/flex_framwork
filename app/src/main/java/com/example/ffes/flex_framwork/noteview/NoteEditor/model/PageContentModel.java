package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.data.Page;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface PageContentModel {
    void getPageContent(String url, int page, OnGetDataCallBack<Page> callback);
}

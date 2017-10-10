package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;


/**
 * Created by Ffes on 2017/10/10.
 */

public interface NoteLoadModel {
    void getPages(String url,OnGetDataCallBack<Page> callBack);
    void getTitleDetail(String url, OnGetDataCallBack<TitleDetail> callBack);
}

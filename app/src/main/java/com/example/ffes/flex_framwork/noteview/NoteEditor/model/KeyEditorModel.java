package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;

import java.util.List;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface KeyEditorModel {
    void getShowPages(String url,OnGetDataCallBack<List<Integer>> callback);
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import java.util.List;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface KeyEditorModel {
    void getShowPages(String url,OnGetDataCallBack<List<Integer>> callback);
}

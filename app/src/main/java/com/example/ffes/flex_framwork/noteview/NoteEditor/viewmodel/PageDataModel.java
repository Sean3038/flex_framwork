package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface PageDataModel {
    void add(String pageurl);
    void remove(int index);
    void setCurrentPage(int page);
}

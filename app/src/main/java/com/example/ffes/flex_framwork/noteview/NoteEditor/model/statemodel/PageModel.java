package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.List;


/**
 * Created by Ffes on 2017/10/11.
 */

public interface PageModel {
    void addPage(Page page);
    void removePage(int index);
    Page getPage(int index);

    void setCurrentPage(int index);
    int  getCurrentPage();
    int getTotalPage();

    List<Page> getAllPage();
}

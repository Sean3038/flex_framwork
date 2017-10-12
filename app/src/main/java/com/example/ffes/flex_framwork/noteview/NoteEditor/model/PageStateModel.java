package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;


/**
 * Created by Ffes on 2017/10/11.
 */

public interface PageStateModel extends SupplyStateModel{

    void addModel(PageDataModel model);
    void removeModel(PageDataModel model);

    void addPage(Page page);
    void removePage(int index);
    Page getPage(int index);

    void setCurrentPage(int index);
    int  getCurrentPage();
    int getTotalPage();
}

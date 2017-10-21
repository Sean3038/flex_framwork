package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Ffes on 2017/10/11.
 */

public interface PageModel {
    void addPage(Page page);
    void removePage(int index);
    void addPage(List<Page> pages);
    Page getPage(int index);

    void setCurrentPage(int index);
    int  getCurrentPage();
    int getTotalPage();

    Map<String,Object> toMap();
}

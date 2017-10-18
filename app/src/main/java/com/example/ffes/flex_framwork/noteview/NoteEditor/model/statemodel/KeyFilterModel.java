package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import java.util.List;

/**
 * Created by Ffes on 2017/10/16.
 */

public interface KeyFilterModel extends PageModel{
    void setfilterkey(List<String> filter);
    int getFilterCount();
    List<String> getAllfilterkey();
    List<String> getfilterkey();
}

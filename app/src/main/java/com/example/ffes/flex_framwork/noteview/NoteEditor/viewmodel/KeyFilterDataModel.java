package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageFilterStateModel;

/**
 * Created by Ffes on 2017/10/16.
 */

public interface KeyFilterDataModel extends DataModel<PageFilterStateModel> {
    void notifyFilterChange();
}

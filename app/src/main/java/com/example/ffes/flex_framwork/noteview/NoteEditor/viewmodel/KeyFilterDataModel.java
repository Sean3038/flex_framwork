package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyFilterModel;

/**
 * Created by Ffes on 2017/10/16.
 */

public interface KeyFilterDataModel extends DataModel<KeyFilterModel> {
    void notifyFilterChange();
    void notifyAddFilterChange();
}

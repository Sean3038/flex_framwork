package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyWordStateModel;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface KeyWordDataModel extends DataModel<KeyWordStateModel>{
    void notifyAddKeyWord();
    void notifyRemoveKeyWord(int index);
}

package com.example.ffes.flex_framwork.noteview.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyWordModel;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface KeyWordDataModel extends DataModel<KeyWordModel>{
    void notifyAddKeyWord();
    void notifyRemoveKeyWord(int key);
}

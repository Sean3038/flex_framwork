package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.KeyWordDataModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface KeyWordStateModel extends StateModel<KeyWordDataModel>{
    void addKeyWord(KeyWord keyWord);
    void removeKeyWord(String key);
    KeyWord getKeyWord(int index);

    int getKeyCount();
}

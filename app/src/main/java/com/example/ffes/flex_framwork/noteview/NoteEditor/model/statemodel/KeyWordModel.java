package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.data.KeyWord;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface KeyWordModel {
    void addKeyWord(KeyWord keyWord);
    void removeKeyWord(int index);
    KeyWord getKeyWord(int index);

    int getKeyCount();
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.data.QA;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface QAModel {
    void addQA(QA qa);
    void removeQA(int index);
    QA getQA(int index);

    int getKeyCount();
}

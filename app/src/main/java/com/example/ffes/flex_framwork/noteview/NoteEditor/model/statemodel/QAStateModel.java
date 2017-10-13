package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.QADataModel;
import com.example.ffes.flex_framwork.noteview.data.QA;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface QAStateModel extends StateModel<QADataModel>{
    void addQA(QA qa);
    void removeQA(String key);
    QA getQA(int index);

    int getKeyCount();
}

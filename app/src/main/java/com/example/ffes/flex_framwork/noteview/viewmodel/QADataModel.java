package com.example.ffes.flex_framwork.noteview.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.QAModel;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface QADataModel extends DataModel<QAModel> {
    void notifyAddQA();
    void notifyRemoveQA(int index);
}

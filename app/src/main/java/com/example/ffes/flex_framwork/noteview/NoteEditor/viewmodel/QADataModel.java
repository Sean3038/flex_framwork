package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.QAStateModel;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface QADataModel extends DataModel<QAStateModel> {
    void notifyAddQA();
    void notifyRemoveQA(int index);
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageStateModel;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface PageDataModel extends DataModel<PageStateModel> {
    void notifyAddPage();
    void notifyRemovePage(int index);
    void notifyCurrentPage(int page);
}

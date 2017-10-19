package com.example.ffes.flex_framwork.noteview.viewmodel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface PageDataModel extends DataModel<PageModel> {
    void notifyAddPage();
    void notifyRemovePage(int index);
    void notifyCurrentPage(int page);
}

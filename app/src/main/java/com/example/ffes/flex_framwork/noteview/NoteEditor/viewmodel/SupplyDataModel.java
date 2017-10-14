package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.SupplyModel;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface SupplyDataModel extends DataModel<SupplyModel>{
    void notifyAddSupply();
    void notifyRemoveSupply(int index);
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.SupplyDataModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface SupplyStateModel extends StateModel<SupplyDataModel>{
    void addSupply(Supply page);
    void removeSupply(int index);
    Supply getSupply(int index);
    int getSupplyCount();
}

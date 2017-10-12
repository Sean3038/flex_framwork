package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.SupplyDataModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface SupplyStateModel {
    void addModel(SupplyDataModel model);
    void removeModel(SupplyDataModel model);

    void addSupply(Supply page);
    void removeSupply(int index);
    Supply getSupply(int index);
    int getSupplyCount();
}

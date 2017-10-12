package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface SupplyDataModel {
    void notifyAddSupply();
    void notifyRemoveSupply(int index);

    void bind(SupplyStateModel pageStateModel);
    void unbind();
}

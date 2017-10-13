package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.TitleDetailDataModel;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface TitleDetailStateModel extends StateModel<TitleDetailDataModel>{
    void setTitleDetail(TitleDetail titleDetail);
    TitleDetail getTitleDetail();
}

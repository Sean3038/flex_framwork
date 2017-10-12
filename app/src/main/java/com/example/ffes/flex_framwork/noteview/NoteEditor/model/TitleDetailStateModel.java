package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.TitleDetailDataModel;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface TitleDetailStateModel {

    void addModel(TitleDetailDataModel model);
    void removeModel(TitleDetailDataModel model);

    void setTitleDetail(TitleDetail titleDetail);
    TitleDetail getTitleDetail();

}

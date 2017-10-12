package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;


import com.example.ffes.flex_framwork.noteview.NoteEditor.model.TitleDetailStateModel;

/**
 * Created by Ffes on 2017/10/12.
 */

public interface TitleDetailDataModel {
    void notifyChange();

    void bind(TitleDetailStateModel pageStateModel);
    void unbind();
}

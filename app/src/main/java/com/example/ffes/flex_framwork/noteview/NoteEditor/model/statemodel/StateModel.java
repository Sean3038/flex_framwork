package com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel;

/**
 * Created by Ffes on 2017/10/14.
 */

public interface StateModel<T> {
    void addModel(T model);
    void removeModel(T model);
}

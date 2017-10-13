package com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel;

/**
 * Created by Ffes on 2017/10/13.
 */

public interface DataModel<T> {
    void bind(T stateModel);
    void unbind();
}

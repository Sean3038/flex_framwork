package com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback;

/**
 * Created by Ffes on 2017/10/11.
 */

public interface OnUpLoadDataCallback<T> {
    void onSuccess(T t);
    void onFailure();
}

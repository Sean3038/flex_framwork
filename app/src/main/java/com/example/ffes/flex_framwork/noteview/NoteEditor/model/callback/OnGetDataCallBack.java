package com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback;

/**
 * Created by Ffes on 2017/10/10.
 */

public interface OnGetDataCallBack<T> {
    void onSuccess(T data);
    void onFailure();
}

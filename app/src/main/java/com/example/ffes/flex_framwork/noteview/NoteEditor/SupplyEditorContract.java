package com.example.ffes.flex_framwork.noteview.NoteEditor;

/**
 * Created by Ffes on 2017/9/18.
 */

public interface SupplyEditorContract {
    interface View{
        void clearInput();
    }
    interface Presenter{
        void addPhoto(String photeUrl);
        void addMessage(String message);
    }
}

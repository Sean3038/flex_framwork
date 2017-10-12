package com.example.ffes.flex_framwork.noteview.NoteEditor;


import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.List;

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

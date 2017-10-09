package com.example.ffes.flex_framwork.noteview.NoteEditor;

import java.util.List;

/**
 * Created by Ffes on 2017/9/25.
 */

public interface KeyEditorContract {
    interface View{
        void showAddKeyDialog();
        void setAddKey(String key);
        void setNoteList(List<Integer> pages);
    }
    interface Presenter{
        void loadData(String noteUrl);
        void clickAdd();
    }
}

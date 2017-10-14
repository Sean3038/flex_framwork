package com.example.ffes.flex_framwork.noteview.NoteEditor;


/**
 * Created by Ffes on 2017/9/18.
 */

public interface NoteEditorContract {
    interface View{
        void setTitleDetail(String title,String color);
        void showTitleEditor(String title,String color);
        void showprogress(String message);
        void closeprogress();
    }

    interface Presenter{
        void loadData(String noteUrl);
        void updateTitleDetail(String noteurl,String title,String color);
        void getTitleDetail(String noteUrl);
        void addPage(String noteUrl,String photurl);
        void saveNote(String noteUrl);
    }
}

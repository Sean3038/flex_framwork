package com.example.ffes.flex_framwork.noteview.NoteEditor;


import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/9/18.
 */

public interface NoteEditorContract {
    interface View{
        void setTitleDetail(String title,String color);
        void showTitleEditor(String title,String color);
        void showprogress(String message);
        void closeprogress();
        void hidenotify();
        void shownotify();
        void setNoteId(String noteurl);
        void end();
    }

    interface Presenter{
        void loadData(String noteUrl);
        void addNote();
        void updateTitleDetail(String noteurl,String title,String color);
        void getTitleDetail(String noteUrl);
        void addPage(String noteUrl,String photurl);
        void addLinkPage(String noteUrl, Map<String,List<String>> notelist);
        void saveNote(String noteUrl);
    }
}

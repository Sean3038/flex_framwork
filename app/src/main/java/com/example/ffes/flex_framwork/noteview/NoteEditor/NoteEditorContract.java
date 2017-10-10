package com.example.ffes.flex_framwork.noteview.NoteEditor;

import java.util.List;

/**
 * Created by Ffes on 2017/9/18.
 */

public interface NoteEditorContract {
    interface View{
        void setPageState(int currentpage,int totalpage);
        void setNoteMenu(List<String> pageContents);
        void setTitleDetail(String title,int color);
        void showTitleEditor(String title,int color);
    }

    interface Presenter{
        void loadData(String noteUrl);
        void removePage(String noteUrl,int page);
        void addPage(String noteUrl,int page);
        void updataTitleDetail(String noteurl,String string,int color);
        void getTitleDetail(String noteUrl);
    }
}

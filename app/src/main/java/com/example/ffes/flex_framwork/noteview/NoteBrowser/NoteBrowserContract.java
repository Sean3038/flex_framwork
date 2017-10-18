package com.example.ffes.flex_framwork.noteview.NoteBrowser;

import java.util.List;

/**
 * Created by Ffes on 2017/9/25.
 */

public interface NoteBrowserContract {
    interface View{

        void showKeyList();

        void hideKeyList();

        void hideQA();

        void showQA();

        void showMessageProgress(String s);

        void closeMessageProgress();

        void setTitle(String title);
    }
    interface Presenter{
        void loadNote(String noteurl);
    }
}

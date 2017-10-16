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

        void showSupply(String noteUrl, List<Integer> page);

        void showQA();

        void hideSupply();

        void setKeyList(List<String> list);

        void setTitle(String title);
    }
    interface Presenter{

        void loadNote(String noteurl);

        void onOpenKeyList();

        void onOpenSupply(List<String> keyList);

        void onOpenQA();

        void onSelectKeyWord(List<String> keyList);
    }
}

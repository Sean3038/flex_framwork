package com.example.ffes.flex_framwork.noteview.NoteBrowser;

import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.List;

/**
 * Created by Ffes on 2017/9/25.
 */

public interface SupplyShowContract {
    interface View{
        void setSupplyList(List<Supply> supply);
    }
    interface Presenter{
        void loadSupply(String noteUrl,int page);
    }
}

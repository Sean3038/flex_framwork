package com.example.ffes.flex_framwork.noteview.NoteEditor;

import android.graphics.Rect;

import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.List;

/**
 * Created by Ffes on 2017/9/20.
 */

public interface NoteShowContract {
    interface View{
        void setKeyData(String noteImageUrl,List<KeyWord> keyData);
        void showAddKeyNotify();
        void hideAddKeyNotify();
    }
    interface Presenter{
        void LoadData(String NoteUrl,int page);
        void saveAllKeyWord(int page,List<KeyWord> keyWords);
        void addKeyWordFrame(int page, KeyWord keyWord);
        void removeKeyWordFrame(int page,String key);
    }
}

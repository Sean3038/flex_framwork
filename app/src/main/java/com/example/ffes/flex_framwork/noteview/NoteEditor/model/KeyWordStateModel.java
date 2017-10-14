package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyWordModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.KeyWordDataModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/14.
 */

public class KeyWordStateModel implements KeyWordModel,StateModel<KeyWordDataModel> {

    Map<String,KeyWord> keyWords;
    List<String> keyset;

    List<KeyWordDataModel> models;

    public KeyWordStateModel(Map<String, KeyWord> keyWords){
        this.keyWords=keyWords;
        this.keyset=new ArrayList<>(keyWords.keySet());
        this.models=new ArrayList<>();
    }

    @Override
    public void addModel(KeyWordDataModel model) {
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(KeyWordDataModel model) {
        models.remove(model);
        model.unbind();
    }

    @Override
    public void addKeyWord(KeyWord keyWord) {
        keyWords.put(keyWord.getKeyword(),keyWord);
        keyset.add(keyWord.getKeyword());
        for(KeyWordDataModel model:models){
            model.notifyAddKeyWord();
        }
    }

    @Override
    public void removeKeyWord(int index) {
        keyWords.remove(keyset.remove(index));
        for(KeyWordDataModel model:models){
            model.notifyRemoveKeyWord(index);
        }
    }

    @Override
    public KeyWord getKeyWord(int index) {
        return keyWords.get(keyset.get(index));
    }

    @Override
    public int getKeyCount() {
        return keyWords.size();
    }
}

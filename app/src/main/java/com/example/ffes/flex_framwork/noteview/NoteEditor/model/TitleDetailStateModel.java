package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.TitleDetailModel;
import com.example.ffes.flex_framwork.noteview.viewmodel.TitleDetailDataModel;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/12.
 */

public class TitleDetailStateModel implements TitleDetailModel,StateModel<TitleDetailDataModel> {

    List<TitleDetailDataModel> models;
    TitleDetail titleDetail;

    public TitleDetailStateModel(){
        models=new ArrayList<>();
        titleDetail=new TitleDetail("#000000","");
    }

    @Override
    public void addModel(TitleDetailDataModel model) {
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(TitleDetailDataModel model) {
        models.remove(model);
        model.unbind();
    }

    @Override
    public void setTitleDetail(TitleDetail titleDetail) {
        this.titleDetail=titleDetail;
        update();
    }

    private void update(){
        for(TitleDetailDataModel model:models){
            model.notifyChange();
        }
    }

    @Override
    public TitleDetail getTitleDetail() {
        return titleDetail;
    }
}

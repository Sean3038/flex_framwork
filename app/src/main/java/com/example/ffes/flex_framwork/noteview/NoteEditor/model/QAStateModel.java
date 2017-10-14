package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.QAModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.QADataModel;
import com.example.ffes.flex_framwork.noteview.data.QA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/14.
 */

public class QAStateModel implements QAModel,StateModel<QADataModel>{

    List<QA> qas;
    List<QADataModel> models;

    public QAStateModel(List<QA> qas){
        this.qas=qas;
        this.models=new ArrayList<>();
    }

    @Override
    public void addModel(QADataModel model) {
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(QADataModel model) {
        models.remove(model);
        model.unbind();
    }

    @Override
    public void addQA(QA qa) {
        qas.add(qa);
        for(QADataModel model:models){
            model.notifyAddQA();
        }
    }

    @Override
    public void removeQA(int index) {
        qas.remove(index);
        for(QADataModel model:models){
            model.notifyRemoveQA(index);
        }
    }

    @Override
    public QA getQA(int index) {
        return qas.get(index);
    }

    @Override
    public int getKeyCount() {
        return qas.size();
    }
}

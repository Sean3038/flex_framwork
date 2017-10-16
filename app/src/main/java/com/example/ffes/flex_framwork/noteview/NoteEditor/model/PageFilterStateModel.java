package com.example.ffes.flex_framwork.noteview.NoteEditor.model;


import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyFilterModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.KeyFilterDataModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/16.
 */

public class PageFilterStateModel implements StateModel<KeyFilterDataModel> , KeyFilterModel,PageModel {

    List<Page> pageList;
    List<String> keylist;
    List<Page> showlist;

    List<KeyFilterDataModel> models;

    int currentPage;

    PageFilterStateModel(){
        pageList=new ArrayList<>();
        keylist=new ArrayList<>();
        models=new ArrayList<>();
        currentPage=-1;
    }

    PageFilterStateModel(List<Page> pageList){
        this.pageList=pageList;
        keylist=new ArrayList<>();
        models=new ArrayList<>();
        currentPage=-1;
    }


    @Override
    public void addPage(Page page) {
        pageList.add(page);
        keylist.retainAll(page.getkeywordlist().keySet());
    }

    @Override
    public void removePage(int index) {

    }

    @Override
    public Page getPage(int index) {
        return showlist.get(index);
    }

    @Override
    public void setCurrentPage(int index) {
        currentPage=index;
    }

    @Override
    public int getCurrentPage() {
        return showlist.size();
    }

    @Override
    public int getTotalPage() {
        return currentPage+1;
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    @Override
    public void setfilterkey(List<String> filter) {
        keylist.retainAll(filter);
        for(Page p:pageList){
            //交集

        }
    }

    @Override
    public void addModel(KeyFilterDataModel model) {
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(KeyFilterDataModel model) {
        models.remove(model);
        model.unbind();
    }
}

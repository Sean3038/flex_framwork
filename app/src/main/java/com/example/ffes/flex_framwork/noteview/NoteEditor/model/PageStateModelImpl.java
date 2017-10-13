package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.CurrentPageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/10.
 */

public class PageStateModelImpl implements PageStateModel,CurrentPageModel {
    List<PageDataModel> models;
    List<Page> pagelist;

    int currentPage;

    public PageStateModelImpl(){
        models=new ArrayList<>();
        pagelist=new ArrayList<>();
        currentPage=-1;
    }

    @Override
    public void addModel(PageDataModel model){
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(PageDataModel model){
        models.remove(model);
        model.unbind();
    }

    @Override
    public void addPage(Page page) {
        pagelist.add(page);
        if(currentPage==-1){
            setCurrentPage(0);
        }
        for(PageDataModel model:models){
            model.notifyAddPage();
        }
    }

    @Override
    public void removePage(int index) {
        pagelist.remove(index);
        if(getCurrentPage() > getTotalPage()){
            setCurrentPage(getTotalPage()-1);
        }
        for(PageDataModel model:models){
            model.notifyRemovePage(index);
        }
    }

    @Override
    public Page getPage(int index) {
        return pagelist.get(index);
    }

    private void updateCurrentPage() {
        for(PageDataModel model:models){
            model.notifyCurrentPage(currentPage);
        }
    }
    @Override
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        updateCurrentPage();
    }
    @Override
    public int getCurrentPage(){
        return currentPage+1;
    }
    @Override
    public int getTotalPage(){
     return pagelist.size();
    }
}

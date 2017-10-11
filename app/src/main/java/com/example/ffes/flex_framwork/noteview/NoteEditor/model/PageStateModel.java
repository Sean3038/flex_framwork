package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/10.
 */

public class PageStateModel{
    List<PageDataModel> models;
    List<Page> pagelist;
    int currentPage;

    public PageStateModel(){
        models=new ArrayList<>();
        pagelist=new ArrayList<>();
        currentPage=0;
    }

    public void addModel(PageDataModel model){
        models.add(model);
        update();
    }

    public void removeModel(PageDataModel model){
        models.remove(model);
    }

    public void add(Page page){
        pagelist.add(page);
        for(PageDataModel model:models){
            model.add(page.getImageurl());
        }
        if(currentPage==0){
            currentPage=1;
            update();
        }
    }

    public void remove(int index){
        pagelist.remove(index);
        for(PageDataModel model:models){
            model.remove(index);
        }

        if(pagelist.size()-1>0 && index<pagelist.size()-1){
            setCurrentPage(index);
        }else{
            setCurrentPage(0);
        }
    }

    private void update() {
        for(PageDataModel model:models){
            model.setCurrentPage(currentPage);
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        update();
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public int getTotalPage(){
     return pagelist.size();
    }
}

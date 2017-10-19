package com.example.ffes.flex_framwork.noteview.NoteEditor.model;


import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyFilterModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.viewmodel.DataModel;
import com.example.ffes.flex_framwork.noteview.viewmodel.KeyFilterDataModel;
import com.example.ffes.flex_framwork.noteview.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/16.
 */

public class PageFilterStateModel implements StateModel<DataModel> , KeyFilterModel,PageModel {

    List<Page> pageList;
    List<String> allkey;

    List<String> fiterkey;
    List<Page> showlist;

    List<DataModel> models;

    int currentPage;

    public PageFilterStateModel(){
        pageList=new ArrayList<>();
        fiterkey =new ArrayList<>();
        models=new ArrayList<>();
        showlist=new ArrayList<>();
        allkey=new ArrayList<>();
        currentPage=-1;
    }

    PageFilterStateModel(List<Page> pageList){
        this.pageList=pageList;
        allkey=new ArrayList<>();
        for(Page p:pageList){
            allkey.addAll(p.getkeywordlist().keySet());
        }
        fiterkey =new ArrayList<>();
        models=new ArrayList<>();
        showlist=new ArrayList<>();
        currentPage=-1;
    }


    @Override
    public void addPage(Page page) {
        pageList.add(page);
        allkey.addAll(page.getkeywordlist().keySet());
        showlist.add(page);
        if(currentPage==-1){
            setCurrentPage(0);
        }
        for(DataModel model:models){
            if(model instanceof PageDataModel){
                ((PageDataModel) model).notifyAddPage();
            }
        }
        for(DataModel model:models){
            if(model instanceof KeyFilterDataModel){
                ((KeyFilterDataModel) model).notifyAddFilterChange();
                Log.d("KeyFilterAdapter",".............");
            }
        }
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
        for(DataModel model:models){
            if(model instanceof PageDataModel){
                ((PageDataModel) model).notifyCurrentPage(index);
            }
        }
    }

    @Override
    public int getCurrentPage() {
        return currentPage+1;
    }

    @Override
    public int getTotalPage() {
        return showlist.size();
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }

    @Override
    public void setfilterkey(List<String> filter) {
        fiterkey.clear();
        fiterkey.addAll(filter);
        showlist.clear();
        Log.d("Filter",filter.size()+"");
        for(Page p:pageList){
            List<String> temp=new ArrayList<>();
            temp.addAll(p.getkeywordlist().keySet());
            temp.retainAll(fiterkey);
            if(temp.size()>0){
                showlist.add(p);
            }
        }
        if(currentPage==0 && showlist.size()==0){
            setCurrentPage(-1);
        }else if(currentPage==-1 && showlist.size()>0){
            setCurrentPage(0);
        }
        for(DataModel model:models){
            if(model instanceof PageDataModel)((PageDataModel) model).notifyAddPage();
        }
        for(DataModel model:models){
            if(model instanceof KeyFilterDataModel)((KeyFilterDataModel) model).notifyFilterChange();
        }
    }

    @Override
    public int getFilterCount() {
        return fiterkey.size();
    }

    @Override
    public List<String> getAllfilterkey() {
        return allkey;
    }

    @Override
    public List<String> getfilterkey() {
        return fiterkey;
    }

    @Override
    public void addModel(DataModel model) {
        models.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(DataModel model) {
        models.remove(model);
        model.unbind();
    }
}

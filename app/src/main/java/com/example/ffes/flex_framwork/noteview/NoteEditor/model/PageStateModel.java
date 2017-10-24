package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import android.net.Uri;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.viewmodel.PageDataModel;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/10.
 */

public class PageStateModel implements PageModel,StateModel<PageDataModel> {

    List<PageDataModel> models;
    List<Page> pagelist;
    ImageRepository imageRepository;
    int currentPage;

    public PageStateModel(){
        models=new ArrayList<>();
        pagelist=new ArrayList<>();
        imageRepository=new ImageRepository(FirebaseStorage.getInstance());
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
        Page p=pagelist.remove(index);

        for(Supply supply:p.getsupplylist()){
            if(supply.getType()==2) {
                imageRepository.removeSupplyPhoto(Uri.parse(supply.getContent()));
            }
        }
        imageRepository.removePagePhoto(Uri.parse(p.getimageurl()));
        if(getCurrentPage() > getTotalPage()){
            setCurrentPage(getTotalPage()-1);
        }
        for(PageDataModel model:models){
            model.notifyRemovePage(index);
        }
    }

    @Override
    public void addPage(List<Page> pages) {
        for(Page page:pages){
            addPage(page);
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

    @Override
    public Map toMap(){
        Map<String,Object> map=new HashMap<>();
        Map<String,Object> pmap=new HashMap<>();
        Map<String,Object> kmap=new HashMap<>();

        HashSet<String> keylist=new HashSet<>();
        int c=0;
        for(Page p:pagelist){
            pmap.put(""+c,p.toMap());
            keylist.addAll(p.getkeywordlist().keySet());
            c++;
        }
        c=0;
        for(String key:keylist){
            kmap.put(""+c,key);
            c++;
        }
        map.put("page",pmap);
        map.put("key",kmap);
        return map;
    }
}

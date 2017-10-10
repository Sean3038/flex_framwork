package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;


/**
 * Created by Ffes on 2017/9/19.
 */

public class SupplyEditPresenter implements SupplyEditorContract.Presenter{
    SupplyEditorContract.View view;
    NoteRepository repository;

    public SupplyEditPresenter(SupplyEditorContract.View view, NoteRepository repository){
        this.view=view;
        this.repository=repository;
    }


    @Override
    public void addPhoto(String noteUrl, int page, final String photeUrl) {
        //view.addSupply(supply);
    }

    @Override
    public void addMessage(String noteUrl, int page, final String message) {
//        if(!message.equals("")) {
//            view.clearInput();
//            view.addSupply(supply);
    }
}

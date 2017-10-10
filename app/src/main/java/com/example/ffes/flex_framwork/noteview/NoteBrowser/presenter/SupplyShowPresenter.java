package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.SupplyShowContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;

/**
 * Created by Ffes on 2017/9/25.
 */

public class SupplyShowPresenter implements SupplyShowContract.Presenter {

    SupplyShowContract.View view;
    NoteRepository repository;

    public SupplyShowPresenter(SupplyShowContract.View view,NoteRepository repository){
        this.view=view;
        this.repository=repository;
    }

    @Override
    public void loadSupply(String noteUrl, final int page) {
        //view.setSupplyList(NoteDecomposeUtil.getSupplyList(note,page));
    }
}

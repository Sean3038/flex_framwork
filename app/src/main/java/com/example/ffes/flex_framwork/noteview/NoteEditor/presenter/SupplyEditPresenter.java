package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.SupplyStateModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;


/**
 * Created by Ffes on 2017/9/19.
 */

public class SupplyEditPresenter implements SupplyEditorContract.Presenter{
    SupplyEditorContract.View view;
    SupplyStateModel supplyStateModel;

    public SupplyEditPresenter(SupplyEditorContract.View view,SupplyStateModel supplyStateModel){
        this.view=view;
        this.supplyStateModel=supplyStateModel;
    }


    @Override
    public void addPhoto(String photeUrl) {
        Supply supply=new Supply(2,photeUrl);
        supplyStateModel.addSupply(supply);
    }

    @Override
    public void addMessage(String message) {
        if(!message.equals("")){
            view.clearInput();
            Supply supply=new Supply(1,message);
            supplyStateModel.addSupply(supply);
        }
    }
}

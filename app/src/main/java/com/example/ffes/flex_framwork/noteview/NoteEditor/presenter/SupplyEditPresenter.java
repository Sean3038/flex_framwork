package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.ImageRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.SupplyModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;


/**
 * Created by Ffes on 2017/9/19.
 */

public class SupplyEditPresenter implements SupplyEditorContract.Presenter{

    SupplyEditorContract.View view;
    SupplyModel supplyStateModel;
    ImageRepository imageRepository;

    public SupplyEditPresenter(SupplyEditorContract.View view, SupplyModel supplyStateModel, ImageRepository imageRepository){
        this.view=view;
        this.supplyStateModel=supplyStateModel;
        this.imageRepository=imageRepository;
    }


    @Override
    public void addPhoto(String noteurl,String photoUrl) {
        imageRepository.addSupplyPhoto(noteurl,photoUrl, new OnUpLoadDataCallback<String>() {

            @Override
            public void onSuccess(String s) {
                Supply supply=new Supply(2,s);
                supplyStateModel.addSupply(supply);
            }

            @Override
            public void onFailure() {

            }
        });
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

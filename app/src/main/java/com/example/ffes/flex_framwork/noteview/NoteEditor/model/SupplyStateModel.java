package com.example.ffes.flex_framwork.noteview.NoteEditor.model;

import android.net.Uri;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.StateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.SupplyModel;
import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.viewmodel.SupplyDataModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/13.
 */

public class SupplyStateModel implements SupplyModel,StateModel<SupplyDataModel> {
    List<SupplyDataModel> supplyModels;
    List<Supply> supplyList;
    ImageRepository imageRepository;

    public SupplyStateModel(List<Supply> supplies){
        supplyModels=new ArrayList<>();
        imageRepository=new ImageRepository(FirebaseStorage.getInstance());
        supplyList=supplies;
    }

    public SupplyStateModel(){
        supplyModels=new ArrayList<>();
        supplyList=new ArrayList<>();
    }

    @Override
    public void addModel(SupplyDataModel model) {
        supplyModels.add(model);
        model.bind(this);
    }

    @Override
    public void removeModel(SupplyDataModel model) {
        supplyModels.remove(model);
        model.unbind();
    }

    @Override
    public void addSupply(Supply supply) {
        supplyList.add(supply);
        for (SupplyDataModel model:supplyModels){
            model.notifyAddSupply();
        }
    }

    @Override
    public void removeSupply(int index) {
        Supply supply=supplyList.remove(index);
        if(supply.getType()==2) {
            imageRepository.removeSupplyPhoto(Uri.parse(supply.getContent()));
        }
        for (SupplyDataModel model:supplyModels){
            model.notifyRemoveSupply(index);
        }
    }

    @Override
    public Supply getSupply(int index) {
        return supplyList.get(index);
    }

    @Override
    public int getSupplyCount() {
        return supplyList.size();
    }
}

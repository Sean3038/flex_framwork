package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.TitleDetailStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.TitleDetailDataModel;
import com.example.ffes.flex_framwork.noteview.widget.UnderlinedTextView;

/**
 * Created by Ffes on 2017/10/14.
 */

public class TitleToolBar implements TitleDetailDataModel{

    ImageView save_btn;
    ImageView supply_btn;
    UnderlinedTextView title;

    TitleDetailStateModel titleDetailStateModel;

    TitleToolBar(ViewGroup viewGroup, View.OnClickListener titleclick, View.OnClickListener saveclick, View.OnClickListener supplyclick){
        save_btn=(ImageView)viewGroup.findViewById(R.id.save_btn);
        supply_btn=(ImageView)viewGroup.findViewById(R.id.supply_btn);
        title=(UnderlinedTextView)viewGroup.findViewById(R.id.title);
        title.setOnClickListener(titleclick);
        save_btn.setOnClickListener(saveclick);
        supply_btn.setOnClickListener(supplyclick);
    }

    @Override
    public void bind(TitleDetailStateModel stateModel) {
        titleDetailStateModel=stateModel;
    }

    @Override
    public void unbind() {
        titleDetailStateModel=null;
    }

    @Override
    public void notifyChange() {
        setTitle(titleDetailStateModel.getTitleDetail().getTitle());
        setUnderLineColor(Color.parseColor(titleDetailStateModel.getTitleDetail().getColor()));
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setUnderLineColor(int color){
        this.title.setUnderLineColor(color);
    }

    public void showSupplyButton(){
        supply_btn.setVisibility(View.VISIBLE);
    }

    public void hideSupplyButton(){
        supply_btn.setVisibility(View.GONE);
    }
}

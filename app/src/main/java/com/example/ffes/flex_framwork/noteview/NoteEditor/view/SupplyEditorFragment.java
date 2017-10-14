package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.ImageRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.SupplyFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.SupplyEditPresenter;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ffes on 2017/9/18.
 */

public class SupplyEditorFragment extends Fragment implements SupplyEditorContract.View{

    public static final String URL_KEY="NoteURL";

    public static final int REQUEST_GETPHOTO=100;

    SupplyEditorContract.Presenter presenter;

    SupplyFragment supplyFragment;

    ImageView save_btn;
    EditText editText;
    ImageView getphoto_btn;
    ImageView enter_btn;

    ScrollView scroll;

    SupplyStateModel stateModel;

    public static SupplyEditorFragment newInstance(String noteUrl, SupplyStateModel model){
        SupplyEditorFragment fragment=new SupplyEditorFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        fragment.setArguments(bundle);
        fragment.setDataModel(model);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.supply_editor_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.editor_detail_toolbar);
        save_btn=(ImageView)getActivity().findViewById(R.id.save_btn);
        editText=(EditText)view.findViewById(R.id.editText);
        getphoto_btn=(ImageView)view.findViewById(R.id.getphoto_btn);
        scroll=(ScrollView)view.findViewById(R.id.scroll);
        enter_btn=(ImageView)view.findViewById(R.id.enter_btn);
        getphoto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addMessage(editText.getText().toString());
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"save supply",Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });
        init();
        presenter=new SupplyEditPresenter(this, stateModel,new ImageRepository(FirebaseStorage.getInstance()));
    }

    public void init(){
        supplyFragment=SupplyFragment.newInstance(stateModel,true);
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.supply,supplyFragment,"SupplyContent");
        ft.commit();
    }

    @Override
    public void clearInput() {
        editText.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GETPHOTO && resultCode==RESULT_OK){
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            for(Image i:images){
                presenter.addPhoto(getArguments().getString(URL_KEY),i.getPath());
                Log.d("Sad",i.getPath());
            }

        }

    }

    public void getPhoto(){
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .showCamera(true)
                .imageTitle("選擇補充照片")
                .single()
                .start(REQUEST_GETPHOTO);
    }

    public void setDataModel(SupplyStateModel model){
        stateModel=model;
    }
}

package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyPageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.SupplyFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.SupplyEditPresenter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.SupplyDataModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.SupplyView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

/**
 * Created by Ffes on 2017/9/18.
 */

public class SupplyEditorFragment extends Fragment implements SupplyEditorContract.View{
    public static final String URL_KEY="NoteURL";
    public static final String PAGE_KEY="Page_key";

    SupplyEditorContract.Presenter presenter;

    SupplyFragment supplyFragment;

    ImageView save_btn;
    EditText editText;
    ImageView getphoto_btn;
    ImageView enter_btn;

    ScrollView scroll;

    SupplyStateModel stateModel;

    public static SupplyEditorFragment newInstance(String noteUrl, int page, SupplyStateModel model){
        SupplyEditorFragment fragment=new SupplyEditorFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        bundle.putInt(PAGE_KEY,page);
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
        presenter=new SupplyEditPresenter(this, stateModel);
    }

    public void init(){
        supplyFragment=SupplyFragment.newInstance(getArguments().getString(URL_KEY),getArguments().getInt(PAGE_KEY),true);
        FragmentManager fm= getChildFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.supply,supplyFragment,"SupplyContent");
        ft.commit();
    }

    @Override
    public void clearInput() {
        editText.setText("");
    }

    public void setDataModel(SupplyStateModel model){
        stateModel=model;
    }
}

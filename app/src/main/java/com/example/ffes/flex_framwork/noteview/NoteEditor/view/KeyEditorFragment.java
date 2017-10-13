package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.NotePageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.KeyEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.KeyEditorPresenter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

/**
 * Created by Ffes on 2017/9/21.
 */

public class KeyEditorFragment extends Fragment implements KeyEditorContract.View{
    public static final String URL_KEY="NoteURL";

    ImageView save_btn;
    ImageView add_btn;

    KeyEditorContract.Presenter presenter;

    public static KeyEditorFragment newInstance(String noteUrl){
        KeyEditorFragment fragment=new KeyEditorFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.key_editor_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setCustomView(R.layout.editor_detail_toolbar);
        save_btn=(ImageView) getActivity().findViewById(R.id.save_btn);
        add_btn=(ImageView) getActivity().findViewById(R.id.add_btn);
        add_btn.setVisibility(View.VISIBLE);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clickAdd();
            }
        });
    }

    @Override
    public void showAddKeyDialog() {
        AddKeyDialogFragment dialogFragment=AddKeyDialogFragment.newInstance(new AddKeyDialogFragment.OnConfirmListener() {
            @Override
            public void onConfirm(String key) {
                presenter.clickAdd();
            }
        });
        dialogFragment.show(getFragmentManager(),"AddKeyDialogFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new KeyEditorPresenter(this,new NoteRepository(FirebaseDatabase.getInstance(),FirebaseStorage.getInstance()));
    }

    @Override
    public void onResume() {
        super.onResume();
        add_btn.setVisibility(View.VISIBLE);
        presenter.loadData(getArguments().getString(URL_KEY));
    }

    @Override
    public void setAddKey(String key) {

    }
}

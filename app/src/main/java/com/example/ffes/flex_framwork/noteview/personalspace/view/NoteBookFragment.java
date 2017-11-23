package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.NoteBookAdapter;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Notebook;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/7.
 */

public class NoteBookFragment extends Fragment implements NoteBookAdapter.OnAddNoteBook{



    public static final int REQUEST_NOTEBOOK=100;

    RecyclerView recyclerView;
    NoteBookAdapter adapter;
    List<Notebook> mData;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notebook_cardview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = new ArrayList<>();
        adapter = new NoteBookAdapter(getContext(),this, mData);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());

        recyclerView = (RecyclerView)view.findViewById(R.id.list_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        prepareNote();
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareNote();
    }

    private void prepareNote() {
        noteRepository.getAllNoteBook(authRepository.getCurrentId(), new OnGetDataCallBack<List<Notebook>>() {
            @Override
            public void onSuccess(List<Notebook> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onAdd() {
        NoteCover.newNoteBook(this,REQUEST_NOTEBOOK);
    }
}



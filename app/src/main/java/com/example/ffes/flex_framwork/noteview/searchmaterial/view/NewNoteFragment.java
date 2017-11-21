package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.NoteAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/11/16.
 */

public class NewNoteFragment extends Fragment {

    RecyclerView recyclerView;
    NoteAdapter adapter;

    NoteRepository noteRepository;

    public static NewNoteFragment newInstance(){
        NewNoteFragment fragment=new NewNoteFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.list_view);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        adapter=new NoteAdapter(getContext(),new ArrayList<SharedNote>());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        initview();
    }

    @Override
    public void onResume() {
        super.onResume();
        initview();
    }

    public void initview(){
        noteRepository.getNewestShareNote(new OnGetDataCallBack<List<SharedNote>>() {
            @Override
            public void onSuccess(List<SharedNote> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }
}

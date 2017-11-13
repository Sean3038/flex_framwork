package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseFragment;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.PersonalNoteAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.widget.ShareNoteDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import static android.Manifest.permission.INTERNET;

/**
 * Created by user on 2017/11/7.
 */

public class SelfNoteFragment extends BaseFragment implements ShareNoteDialog.OnClickShare,PersonalNoteAdapter.OnAddNote{

    String[] permissions = new String[]{INTERNET};
    public static final int GET_INTERNET = 0;

    RecyclerView recyclerView;
    SearchView searchView;
    PersonalNoteAdapter adapter;
    NoteRepository noteRepository;
    AuthRepository authRepository;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GET_INTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    getActivity().finish();
                }
                return;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selfnote, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView)view.findViewById(R.id.list_view);
        searchView=(SearchView)view.findViewById(R.id.search_view);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
        adapter = new PersonalNoteAdapter(this,this, getFragmentManager());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), permissions, GET_INTERNET);
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        initSearch();
        prepareNote();
    }

    private void prepareNote() {
        noteRepository.getPersonalSpaceAllNoteData(authRepository.getCurrentId(), new OnGetDataCallBack<List<PersonalNote>>() {
            @Override
            public void onSuccess(List<PersonalNote> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initSearch(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()==0){
                    prepareNote();
                }
                return false;
            }
        });
    }

    private void search(String key){
        noteRepository.getPersonalNoteByKey(authRepository.getCurrentId(), key, new OnGetDataCallBack<List<PersonalNote>>() {
            @Override
            public void onSuccess(List<PersonalNote> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onShare(String noteurl, String college, String dep) {
        NoteRepository repository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        AuthRepository auth=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());

        if(college!=null && dep!=null) {
            showProgress("分享中.....");
            repository.share(auth.getCurrentId(), noteurl, college, dep, new OnUpLoadDataCallback() {
                @Override
                public void onSuccess(Object o) {
                    dismissProgress();
                }

                @Override
                public void onFailure() {
                    dismissProgress();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareNote();
    }

    @Override
    public void onAdd() {
        Toast.makeText(getContext(),"個人空間", Toast.LENGTH_SHORT).show();
        NoteEditorActivity.start(getContext(),null);
    }
}

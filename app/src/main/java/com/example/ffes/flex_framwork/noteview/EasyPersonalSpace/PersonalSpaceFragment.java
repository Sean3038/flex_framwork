package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.widget.ShareNoteDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ffes on 2017/10/29.
 */

public class PersonalSpaceFragment extends BaseFragment  implements ShareNoteDialog.OnClickShare {

    @BindView(R.id.notelist)
    RecyclerView notelist;
    Unbinder unbinder;

    PersonalNoteAdapter adapter;

    public static PersonalSpaceFragment newInstance() {
        PersonalSpaceFragment fragment = new PersonalSpaceFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.refresh();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personalspace, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new PersonalNoteAdapter(this,getFragmentManager());
        notelist.setLayoutManager(new GridLayoutManager(this.getActivity(),3,GridLayoutManager.VERTICAL,false));
        notelist.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}

package com.example.ffes.flex_framwork.noteview.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.widget.CircleTransformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AccountFragment extends BaseFragment{

    public static final int REQUEST_PROFILE_EDIT=99;

    ImageButton out;
    ImageButton edit;
    CircularImageView user;
    TextView username;
    TextView information;
    RecyclerView sharedlist;

    AuthRepository auth;

    public static AccountFragment newIstance(){
        AccountFragment fragment=new AccountFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(auth.getCurrentId()!=null) {
            initData();
            initFragment();
            initAdapter();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        username=(TextView)view.findViewById(R.id.username);
        sharedlist=(RecyclerView)view.findViewById(R.id.sharedlist);
        information=(TextView)view.findViewById(R.id.information);
        out = (ImageButton) view.findViewById(R.id.out);
        edit = (ImageButton) view.findViewById(R.id.edit);
        user = (CircularImageView) view.findViewById(R.id.user);
        out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("訊息")
                        .setMessage("確定要登出此帳號？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialog, int which) {
                                auth.revoke();
                            } }).create() .show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditSelfFile.startAction(AccountFragment.this,REQUEST_PROFILE_EDIT);
            }
        });
    }

    private void initAdapter() {
            NoteRepository noterepository = new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
            noterepository.getSelfSharedNote(auth.getCurrentId(), new OnGetDataCallBack<List<SharedNote>>() {
                @Override
                public void onSuccess(List<SharedNote> data) {
                    SelfSharedNoteAdapter adapter = new SelfSharedNoteAdapter(data);
                    sharedlist.setAdapter(adapter);
                    sharedlist.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
                }

                @Override
                public void onFailure() {

                }
            });
    }

    private void initFragment() {

    }

    private void initData() {
        auth.getUser(auth.getCurrentId(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                Picasso.with(getContext()).load(data.getPhotoUrl())
                        .noFade()
                        .resize(400,400)
                        .noPlaceholder()
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(user);
                username.setText(data.getName());
                information.setText(data.getInfo());
                dismissProgress();
            }

            @Override
            public void onFailure() {
                dismissProgress();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_PROFILE_EDIT:
                if(resultCode==RESULT_OK){
                    initData();
                }
                break;
        }
    }
}

package com.example.ffes.flex_framwork.noteview.linknote.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.ViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ShareNoteBrowser extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    public static final String NOTEURL_KEY="noteurl";

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String[] titles = new String[]{"筆記", "評論"};
    List<Fragment> fragments;

    Toolbar toolbar;

    TextView note_name;

    ImageButton like;
    ImageButton link;

    boolean pressPictureFlag;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    String noteurl;
    boolean isliked=false;
    boolean islinked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_note);
        noteurl=getIntent().getExtras().getString(NOTEURL_KEY);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
        noteRepository.updateLook(authRepository.getCurrentId(),noteurl);
        initUI();
        initnote();
    }

    private void initUI(){
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.fl);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        note_name = (TextView)findViewById(R.id.note_name);
        like = (ImageButton)findViewById(R.id.like);
        link = (ImageButton)findViewById(R.id.link);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeliked();
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isliked) {
                    changelinked();
                }else{
                    new AlertDialog.Builder(ShareNoteBrowser.this)
                            .setTitle("訊息")
                            .setMessage("你必須給予喜歡才能引用此筆記")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                } }).create() .show();
                }
            }
        });
    }

    private void initFragment(String uid,String noteurl,String photourl){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String tab:titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.addOnTabSelectedListener(this);
        fragments=new ArrayList<>();
        fragments.add(NoteFragment.newInstance(noteurl,uid,photourl));
        fragments.add(MessageFragment.newInstance(noteurl));
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initnote(){
        noteRepository.checkLike(authRepository.getCurrentId(), noteurl, new OnGetDataCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                isliked=data;
                if(data){
                    like.setBackgroundResource(R.drawable.like);
                }else{
                    like.setBackgroundResource(R.drawable.no_like);
                }
            }

            @Override
            public void onFailure() {

            }
        });
        noteRepository.checkLink(authRepository.getCurrentId(), noteurl, new OnGetDataCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                islinked=data;
                if(data){
                    link.setBackgroundResource(R.drawable.link);
                }else{
                    link.setBackgroundResource(R.drawable.no_link);
                }
            }

            @Override
            public void onFailure() {

            }
        });
        noteRepository.getShareNote(noteurl, new OnGetDataCallBack<SharedNote>() {
            @Override
            public void onSuccess(SharedNote data) {
                note_name.setText(data.getTitle());
                initFragment(data.getUid(),data.getId(),data.getPhotoUrl());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void changeliked(){
        isliked=!isliked;
        noteRepository.updateLike(authRepository.getCurrentId(),noteurl,isliked);
        if(isliked){
            like.setBackgroundResource(R.drawable.like);
        }else{
            like.setBackgroundResource(R.drawable.no_like);
        }
    }

    private void changelinked(){
        islinked=!islinked;
        noteRepository.updateLink(authRepository.getCurrentId(),noteurl,islinked);
        if(islinked){
            link.setBackgroundResource(R.drawable.link);
        }else{
            link.setBackgroundResource(R.drawable.no_link);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public static void start(Context context,String noteurl){
        Intent intent=new Intent(context,ShareNoteBrowser.class);
        Bundle bundle=new Bundle();
        bundle.putString(NOTEURL_KEY,noteurl);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

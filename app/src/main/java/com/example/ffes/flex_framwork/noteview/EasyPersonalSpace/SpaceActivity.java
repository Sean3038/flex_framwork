package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.personalspace.view.NoteSpaceFragment;

public class SpaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space);

        init();
    }

    public void init(){
        initPersonalSpaceFragment();
    }

    public void initPersonalSpaceFragment(){
//        NoteSpaceFragment fragment=NoteSpaceFragment.newInstance();
//        FragmentManager fm=getSupportFragmentManager();
//        FragmentTransaction ft=fm.beginTransaction();
//        ft.replace(R.id.personal,fragment,"NoteSpaceFragment");
//        ft.commit();
//        Log.d("NoteSpaceFragment","START");
        NoteSpaceFragment fragment= NoteSpaceFragment.newInstance();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.content,fragment,"NoteSpaceFragment");
        ft.commit();
        Log.d("NoteSpaceFragment","START");
    }

    public static void start(Context context){
        Intent intent=new Intent();
        intent.setClass(context,SpaceActivity.class);
        context.startActivity(intent);
    }
}

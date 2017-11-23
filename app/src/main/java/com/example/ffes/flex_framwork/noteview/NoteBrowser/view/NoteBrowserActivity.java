package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.NotePageAdapter;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter.NoteBrowserPresenter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyFilterAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageFilterStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.PageIndicator;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.SupplyWindow;
import com.example.ffes.flex_framwork.noteview.widget.HackyViewPager;
import com.example.ffes.flex_framwork.noteview.widget.LottieButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class NoteBrowserActivity extends BaseActivity implements NoteBrowserContract.View,View.OnClickListener {
    public static final String URL_KEY="NoteURL";
    public static final String UID_KEY="uid";

    FilterToolBar filterToolBar;
    PageIndicator pageIndicator;
    SupplyWindow supplyView;

    RecyclerView keylistcontent;
    KeyFilterAdapter keyFilterAdapter;

    HackyViewPager notewindow;
    NotePageAdapter notePageAdapter;

    FrameLayout notifynokey;

    NoteBrowserContract.Presenter noteBrowserPresenter;

    PageFilterStateModel pageFilterStateModel;

    String noteurl;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        initView();

        Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null){
            noteurl=bundle.getString(URL_KEY);
            uid=bundle.getString(UID_KEY);
        }

        noteBrowserPresenter=new NoteBrowserPresenter(this,pageFilterStateModel,new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()),new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance()));
        noteBrowserPresenter.loadNote(uid,noteurl);
    }

    @Override
    public void onBackPressed() {
        if(supplyView.isOpened()==true){
            supplyView.hideSupplyWindow();
            filterToolBar.setToggleEnable(true);
            filterToolBar.hideKeyList();
        }else{
            super.onBackPressed();
            overridePendingTransition(R.anim.right_1,R.anim.right_2);
        }
    }

    private void initAdapter(){
        pageFilterStateModel=new PageFilterStateModel();
        keyFilterAdapter =new KeyFilterAdapter();
        notePageAdapter=new NotePageAdapter(getSupportFragmentManager());
        notePageAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(pageFilterStateModel.getTotalPage()==0){
                    showFilterNotify();
                    filterToolBar.hideSupplyToggle();
                }else{
                    hideFilterNotfiy();
                    filterToolBar.showSupplyToggle();
                }
            }
        });
        pageFilterStateModel.addModel(keyFilterAdapter);
        pageFilterStateModel.addModel(notePageAdapter);
    }

    private void initView(){
        setContentView(R.layout.activity_note_browser);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.function_toolbar);

        //UI綁定

        filterToolBar=new FilterToolBar(getSupportActionBar().getCustomView(),new LottieButton.Callback() {
            @Override
            public void onOpen() {
                showKeyList();
            }

            @Override
            public void onClose() {
                hideKeyList();
            }
        },this,this);
        pageIndicator=new PageIndicator((ViewGroup)findViewById(R.id.pageindicator));
        pageFilterStateModel.addModel(pageIndicator);
        supplyView=new SupplyWindow((ViewGroup)findViewById(R.id.supplylayout));
        notewindow=(HackyViewPager)findViewById(R.id.notestage);
        notifynokey=(FrameLayout)findViewById(R.id.notifynokey);
        notewindow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageFilterStateModel.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        keylistcontent=new RecyclerView(this);

        //關鍵字列表設定
        keylistcontent.setBackgroundColor(ContextCompat.getColor(this, R.color.keyListBackgroundColor));
        keylistcontent.setAdapter(keyFilterAdapter);
        keylistcontent.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //關鍵字視窗設定
        PopupWindow keylistwindow=new PopupWindow(500,400);
        keylistwindow.setContentView(keylistcontent);

        filterToolBar.setPopupWindow(keylistwindow);
        //補充視窗設定

        //筆記內容
        notewindow.setAdapter(notePageAdapter);
    }

    @Override
    public void showKeyList() {
        filterToolBar.showKeyList();
    }

    @Override
    public void hideKeyList(){
        filterToolBar.hideKeyList();
    }

    @Override
    public void hideQA() {

    }

    @Override
    public void showQA() {

    }

    @Override
    public void setTitle(String title) {
        filterToolBar.setTitle(title);
    }

    public void showFilterNotify(){
        notifynokey.setVisibility(View.VISIBLE);
    }

    public void hideFilterNotfiy(){
        notifynokey.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.supplytoggle:
                //按下補充開關
                if(!supplyView.isOpened()){
                    supplyView.showSuupplyWindow(pageFilterStateModel.getPage(pageFilterStateModel.getCurrentPage()-1).getsupplylist(),getSupportFragmentManager());
                    filterToolBar.setToggleEnable(false);
                }else{
                    supplyView.hideSupplyWindow();
                    filterToolBar.setToggleEnable(true);
                }
                break;
            case R.id.title:
                //按下標題時

                break;
        }
    }

    public void showMessageProgress(String message){
        showProgress(message);
    }

    public void closeMessageProgress(){
        dismissProgress();
    }

    public static void start(Context context,String uid, String noteUrl){
        Intent intent=new Intent();
        intent.setClass(context,NoteBrowserActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        bundle.putString(UID_KEY,uid);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

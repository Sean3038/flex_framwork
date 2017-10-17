package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.NotePageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyPageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter.NoteBrowserPresenter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyFilterAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageFilterStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyFilterModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.PageIndicator;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.SupplyWindow;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.HackyViewPager;
import com.example.ffes.flex_framwork.noteview.widget.LottieButton;
import com.example.ffes.flex_framwork.noteview.widget.SupplyView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class NoteBrowserActivity extends BaseActivity implements NoteBrowserContract.View,View.OnClickListener {

    FilterToolBar filterToolBar;
    PageIndicator pageIndicator;
    SupplyWindow supplyView;

    RecyclerView keylistcontent;
    KeyFilterAdapter keyFilterAdapter;

    HackyViewPager notewindow;
    NotePageAdapter notePageAdapter;

    NoteBrowserContract.Presenter noteBrowserPresenter;

    PageFilterStateModel pageFilterStateModel;

    String noteurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAdapter();
        initView();


        noteurl="sdf4K5df6a";
        noteBrowserPresenter=new NoteBrowserPresenter(this,pageFilterStateModel,new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()));
        noteBrowserPresenter.loadNote(noteurl);
    }

    @Override
    public void onBackPressed() {
        if(supplyView.isOpened()==true){
            supplyView.hideSupplyWindow();
        }else{
            super.onBackPressed();
        }
    }

    private void initAdapter(){
        pageFilterStateModel=new PageFilterStateModel();
        keyFilterAdapter =new KeyFilterAdapter();
        notePageAdapter=new NotePageAdapter(getSupportFragmentManager());
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
                keyFilterAdapter.getSelectedKeyList();
            }
        },this,this);
        pageIndicator=new PageIndicator((ViewGroup)findViewById(R.id.pageindicator));
        pageFilterStateModel.addModel(pageIndicator);
        supplyView=new SupplyWindow((ViewGroup)findViewById(R.id.supplylayout));
        notewindow=(HackyViewPager)findViewById(R.id.notestage);
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
        PopupWindow keylistwindow=new PopupWindow();
        keylistwindow.setContentView(keylistcontent);
        keylistwindow.setElevation(10f);
        keylistwindow.setWidth(400);
        keylistwindow.setHeight(600);
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


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.supplytoggle:
                //按下補充開關
                if(!supplyView.isOpened()){
                    supplyView.showSuupplyWindow(pageFilterStateModel.getPage(pageFilterStateModel.getCurrentPage()-1).getsupplylist(),getSupportFragmentManager());
                }else{
                    supplyView.hideSupplyWindow();
                }
                break;
            case R.id.title:
                //按下標題時

                break;
        }
    }
}

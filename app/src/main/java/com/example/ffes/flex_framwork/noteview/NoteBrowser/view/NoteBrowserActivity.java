package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.NotePageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyPageAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter.NoteBrowserPresenter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.KeyFilterAdapter;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.HackyViewPager;
import com.example.ffes.flex_framwork.noteview.widget.LottieButton;
import com.example.ffes.flex_framwork.noteview.widget.SupplyView;

import java.util.List;

public class NoteBrowserActivity extends AppCompatActivity implements NoteBrowserContract.View,View.OnClickListener {

    LottieButton keylisttoggle;
    ImageView supplytoggle;
    TextView titleview;
    TextView currentpage;
    TextView totalpage;
    SupplyView supplyView;

    PopupWindow keylistwindow;
    RecyclerView keylistcontent;
    KeyFilterAdapter keyFilterAdapter;

    HackyViewPager notewindow;
    NotePageAdapter notePageAdapter;



    NoteBrowserContract.Presenter noteBrowserPresenter;

    boolean isSupplyOpened=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        noteBrowserPresenter=new NoteBrowserPresenter(this, NoteRepository.getInstance());
    }

    @Override
    public void onBackPressed() {
        if(supplyView.getVisibility()==View.VISIBLE){
            closeSupply();
        }else{
            super.onBackPressed();
        }
    }

    private void initView(){
        setContentView(R.layout.activity_note_browser);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.function_toolbar);

        //UI綁定
        keylisttoggle=(LottieButton)findViewById(R.id.keylisttoggle);
        titleview=(TextView)findViewById(R.id.title);
        supplytoggle=(ImageView)findViewById(R.id.supplytoggle);
        currentpage=(TextView)findViewById(R.id.currentpage);
        totalpage=(TextView)findViewById(R.id.totalpage);
        supplyView=(SupplyView)findViewById(R.id.supplyview);
        notewindow=(HackyViewPager)findViewById(R.id.notestage);

        keylistcontent=new RecyclerView(this);
        keyFilterAdapter =new KeyFilterAdapter();

        keylistwindow=new PopupWindow();

        notePageAdapter=new NotePageAdapter(getSupportFragmentManager());

        //關鍵字列表開關設定
        keylisttoggle.setOnCallbackListener(new LottieButton.Callback() {
            @Override
            public void onOpen() {
                showKeyList();
            }

            @Override
            public void onClose() {
                hideKeyList();
            }
        });
        //補充頁開關設定
        supplytoggle.setOnClickListener(this);
        //標題編輯開關設定
        titleview.setOnClickListener(this);
        //關鍵字列表設定
        keylistcontent.setBackgroundColor(ContextCompat.getColor(this, R.color.keyListBackgroundColor));
        keylistcontent.setAdapter(keyFilterAdapter);
        keylistcontent.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //補充內容設定
        supplyView.setSupplyPageAdapter(new SupplyPageAdapter(getSupportFragmentManager()));
        //關鍵字視窗設定
        keylistwindow.setContentView(keylistcontent);
        keylistwindow.setElevation(10f);
        keylistwindow.setWidth(400);
        keylistwindow.setHeight(600);
        //補充視窗設定

        //筆記內容
        notewindow.setAdapter(notePageAdapter);
        notewindow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentPage(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initVariable(){

    }

    private void loadata(){

    }
    @Override
    public void showKeyList() {
        keylistwindow.showAsDropDown(keylisttoggle,0,84);
    }
    @Override
    public void hideKeyList(){
        keylistwindow.dismiss();
    }

    @Override
    public void hideQA() {

    }

    @Override
    public void showSupply(String noteUrl,List<Integer> page) {
        supplyView.changeData(noteUrl,page);
        supplyView.setVisibility(View.VISIBLE);
    }

    public void closeSupply(){
        supplyView.setVisibility(View.GONE);
    }

    @Override
    public void showQA() {

    }

    @Override
    public void hideSupply() {

    }

    @Override
    public void setKeyList(List<String> list) {
        keyFilterAdapter.add(list);
    }

    @Override
    public void setTitle(String title) {
         titleview.setText(title);
    }

    @Override
    public void setNotePage(List<Integer> list) {
        notePageAdapter.setNoteList(list);
        setTotalPage(list.size());
    }

    @Override
    public void setCurrentPage(int page) {
        currentpage.setText(""+page);
    }

    @Override
    public void setTotalPage(int page) {
        totalpage.setText(""+page);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.supplytoggle:
                //按下補充開關
                if(!isSupplyOpened){
                    noteBrowserPresenter.onOpenSupply(keyFilterAdapter.getSelectedKeyList());
                    isSupplyOpened=true;
                }else{
                    closeSupply();
                    isSupplyOpened=false;
                }
                break;
            case R.id.title:
                //按下標題時
                noteBrowserPresenter.onOpenQA();
                break;
        }
    }
}

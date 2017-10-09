package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.NoteViewAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.PageListAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.NoteEidtorPresenter;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.example.ffes.flex_framwork.noteview.widget.NoteTitleDialogFragment;
import com.example.ffes.flex_framwork.noteview.widget.UnderlinedTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteEditorActivity extends AppCompatActivity implements NoteEditorContract.View,NoteTitleDialogFragment.Callback,
        PageListAdapter.OnAddPageListener,PageListAdapter.OnSelectItemListener,OnImageClick{

    public static final String URL_KEY="NoteURL";

    ImageView save_btn;
    ImageView supply_btn;
    UnderlinedTextView title;

    LinearLayout editor_layout;
    LinearLayout pageindicator;
    TextView editor_btn;
    RecyclerView pagelistview;

    TextView currentpage;
    TextView totalpage;


    ViewPager content;
    boolean isPageEditorOpened=true;
    boolean isEditor=false;

    PageListAdapter pageListAdapter;


    SupplyEditorFragment supplyEditorFragment;
    KeyEditorFragment keyEditorFragment;
    NoteViewAdapter noteViewAdapter;

    TextView notifyNoPage;

    NoteEditorContract.Presenter presenter;

    String noteUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editor_toolbar);
        Intent intent=getIntent();
        if(intent!=null){
            noteUrl=intent.getStringExtra(URL_KEY);
        }
        initUI();
        initToolBar();
        initAdapter();
        init();
        presenter=new NoteEidtorPresenter(this, NoteRepository.getInstance());
        presenter.loadData(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init(){
        editor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView btn=(TextView)v;
                if(isPageEditorOpened){
                    isPageEditorOpened=false;
                    btn.setText("編輯");

                    //關閉編輯

                    pageListAdapter.setEditor(isPageEditorOpened);

                }else{
                    isPageEditorOpened=true;
                    btn.setText("完成");

                    //開啟編輯

                    pageListAdapter.setEditor(isPageEditorOpened);
                }
            }
        });

        pageindicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEditor){
                    closePageEditor();
                    isEditor=false;
                }else{
                    openPageEditor();
                    isEditor=true;
                }

            }
        });
        content.setAdapter(noteViewAdapter);
        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPageState(position+1,noteViewAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        noteViewAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(noteViewAdapter.getCount()>0) {
                    setPageState(content.getCurrentItem()+1, noteViewAdapter.getCount());
                    supply_btn.setVisibility(View.VISIBLE);
                    notifyNoPage.setVisibility(View.GONE);
                }else{
                    setPageState(0,0);
                    supply_btn.setVisibility(View.GONE);
                    notifyNoPage.setVisibility(View.VISIBLE);
                }
            }
        });
        pagelistview.setAdapter(pageListAdapter);
        pagelistview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    private void initUI(){
        content=(ViewPager)findViewById(R.id.content);
        editor_layout=(LinearLayout)findViewById(R.id.editor_layout);
        editor_btn=(TextView)findViewById(R.id.editor_btn);
        notifyNoPage=(TextView)findViewById(R.id.notifyNoPage);
        pageindicator=(LinearLayout)findViewById(R.id.pageindicator);
        pagelistview=(RecyclerView)findViewById(R.id.pagelistview);
        currentpage=(TextView)findViewById(R.id.currentpage);
        totalpage=(TextView)findViewById(R.id.totalpage);
    }

    private void initAdapter(){
        noteViewAdapter=new NoteViewAdapter(this,this);
        pageListAdapter=new PageListAdapter(this);
        pageListAdapter.setListener(this);
    }

    private void initToolBar(){
        save_btn=(ImageView)findViewById(R.id.save_btn);
        supply_btn=(ImageView)findViewById(R.id.supply_btn);
        title=(UnderlinedTextView)findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getTitleDetail(null);
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"add page",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        supply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSupplyEdit();
            }
        });
    }

    public void showTitleEditor(String title,int color){
        FragmentManager fragmentManager=getSupportFragmentManager();
        NoteTitleDialogFragment.newInstance(title,color,this).show(fragmentManager,"dialog");
    }


    public void openPageEditor(){
        editor_layout.setVisibility(View.VISIBLE);
    }

    public void closePageEditor(){
        editor_layout.setVisibility(View.GONE);
    }

    public void openSupplyEdit(){
        supplyEditorFragment = SupplyEditorFragment.newInstance("dsaf",noteViewAdapter.get(content.getCurrentItem()).getPage());
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.supplyfragment,supplyEditorFragment,"SupplyFragment");
        ft.addToBackStack("SupplyFragment");
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount()<1){
                    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    getSupportActionBar().setCustomView(R.layout.editor_toolbar);
                    initToolBar();
                }
            }
        });
        ft.commit();
    }

    public void openKeyEdit(int page){
        if(keyEditorFragment==null) {
            keyEditorFragment = KeyEditorFragment.newInstance("dsaf");
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.supplyfragment,keyEditorFragment,"KeyEditorFragment");
        ft.addToBackStack("KeyEditorFragment");
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount()<1){
                    getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    getSupportActionBar().setCustomView(R.layout.editor_toolbar);
                    initToolBar();
                }
            }
        });
        ft.commit();
    }

    @Override
    public void onAddPage() {
        Toast.makeText(this,"add page",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickImage(int page) {
        openKeyEdit(page);
    }

    @Override
    public void setPageState(int currentpage, int totalpage) {
        this.totalpage.setText(totalpage+"");
        this.currentpage.setText(currentpage+"");
    }

    @Override
    public void setNoteMenu(List<PageContent> pageContents) {
        noteViewAdapter.setPageList(pageContents);
        pageListAdapter.setPageList(pageContents);
    }

    @Override
    public void setTitleDetail(String title, int color) {
        this.title.setText(title);
        this.title.setUnderLineColor(color);
    }

    @Override
    public void onConfirm(String title, int color) {
        setTitleDetail(title,color);
    }

    @Override
    public void onSelect(int position) {
        content.setCurrentItem(position);
        currentpage.setText((position+1)+"");
    }

    @Override
    public void onDelete(int page,int position) {
        presenter.removePage(null,page);
        noteViewAdapter.remove(position);
    }
}

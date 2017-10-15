package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.ImageRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteFragment;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.NoteViewAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.PageListAdapter;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyWordStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.QAStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.TitleDetailStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.presenter.NoteEidtorPresenter;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.NoteTitleDialogFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteEditorActivity extends BaseActivity implements NoteEditorContract.View,NoteTitleDialogFragment.Callback,
        PageListAdapter.OnAddPageListener,PageListAdapter.OnSelectItemListener,OnImageClick{

    public static final String URL_KEY="NoteURL";
    private static final int REQUEST_GETPHOTO = 100;

    LinearLayout editor_layout;
    TextView editor_btn;
    RecyclerView pagelistview;

    PageIndicator pageIndicator;
    TitleToolBar titleToolBar;

    ViewPager content;

    boolean isPageEditorOpened=true;
    boolean isEditor=false;

    PageListAdapter pageListAdapter;

    NoteViewAdapter noteViewAdapter;

    TextView notifyNoPage;

    NoteEditorContract.Presenter presenter;

    PageStateModel stateModel;
    TitleDetailStateModel titleDetailStateModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.editor_toolbar);

        initUI();
        initToolBar();
        initAdapter();
        init();


        presenter=new NoteEidtorPresenter(this, new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()),new ImageRepository(FirebaseStorage.getInstance()),stateModel,titleDetailStateModel);
        presenter.loadData("sdf4K5df6a");
    }

    private void init(){
        editor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView btn=(TextView)v;
                if(isPageEditorOpened){
                    isPageEditorOpened=false;
                    btn.setText("編輯");
                    pageListAdapter.setEditor(isPageEditorOpened);

                }else{
                    isPageEditorOpened=true;
                    btn.setText("完成");
                    pageListAdapter.setEditor(isPageEditorOpened);
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
                stateModel.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        pagelistview=(RecyclerView)findViewById(R.id.pagelistview);

        LinearLayout pagestatelayout=(LinearLayout) findViewById(R.id.pageindicator);
        pagestatelayout.setOnClickListener(new View.OnClickListener() {
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
        pageIndicator=new PageIndicator(pagestatelayout);
    }

    private void initAdapter(){
        noteViewAdapter=new NoteViewAdapter(this,this);
        pageListAdapter=new PageListAdapter(this);
        pageListAdapter.setListener(this);

        stateModel=new PageStateModel();
        stateModel.addModel(pageIndicator);
        stateModel.addModel(pageListAdapter);
        stateModel.addModel(noteViewAdapter);
        titleDetailStateModel=new TitleDetailStateModel();
        titleDetailStateModel.addModel(titleToolBar);
    }

    private void initToolBar(){
        titleToolBar=new TitleToolBar((ViewGroup)findViewById(R.id.toolbarroot)
                ,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getTitleDetail("sdf4K5df6a");
                        Toast.makeText(v.getContext(),"click", Toast.LENGTH_SHORT).show();
                    }
                }
                ,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.saveNote("sdf4K5df6a");
                    }
                }
                ,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {openSupplyEdit(stateModel.getPage(stateModel.getCurrentPage()-1).getsupplylist());
                    }
                });
        if(stateModel!=null){
            if(stateModel.getTotalPage()==0) {
                titleToolBar.hideSupplyButton();
            }
            setTitleDetail(titleDetailStateModel.getTitleDetail().getTitle(),titleDetailStateModel.getTitleDetail().getColor());
        }
    }

    public void showTitleEditor(String title,String color){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        NoteTitleDialogFragment.newInstance(title,color,this).show(ft,"Dialog");
    }

    @Override
    public void showprogress(String message) {
        showProgress(message);
    }


    @Override
    public void closeprogress() {
        dismissProgress();
    }

    @Override
    public void hidenotify() {
            titleToolBar.showSupplyButton();
            notifyNoPage.setVisibility(View.GONE);
    }

    @Override
    public void end() {
        finish();
    }


    public void openPageEditor(){
        editor_layout.setVisibility(View.VISIBLE);
    }

    public void closePageEditor(){
        editor_layout.setVisibility(View.GONE);
    }

    public void openSupplyEdit(List<Supply> supplies){
        SupplyEditorFragment supplyEditorFragment = SupplyEditorFragment.newInstance("sdf4K5df6a",new SupplyStateModel(supplies));
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

    public void openKeyEdit(String imageurl,Map<String,KeyWord> keyWords, List<QA> qaList){
        NoteFragment noteFragment=NoteFragment.newInstance(imageurl,new KeyWordStateModel(keyWords),new QAStateModel(qaList),true);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.supplyfragment,noteFragment,"NoteFragment");
        ft.addToBackStack("NoteFragment");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GETPHOTO && resultCode==RESULT_OK){
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            for(Image i:images){
                presenter.addPage("sdf4K5df6a",i.getPath());
            }

        }

    }


    @Override
    public void onAddPage() {
        getPhoto();
        Toast.makeText(this,"add page",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickImage(int page) {
        openKeyEdit(stateModel.getPage(page).getimageurl(),stateModel.getPage(page).getkeywordlist(),stateModel.getPage(page).getqalist());
    }

    @Override
    public void setTitleDetail(String title,String color) {
        titleToolBar.setTitle(title);
        titleToolBar.setUnderLineColor(Color.parseColor(color));
    }

    @Override
    public void onConfirm(String title, String color) {
        presenter.updateTitleDetail("sdf4K5df6a",title,color);
    }

    @Override
    public void onSelect(int position) {
        stateModel.setCurrentPage(position);
        content.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        stateModel.removeModel(pageListAdapter);
        stateModel.removeModel(noteViewAdapter);
        stateModel.removeModel(pageIndicator);
        titleDetailStateModel.removeModel(titleToolBar);
        super.onDestroy();
    }

    @Override
    public void onDelete(int position) {
        stateModel.removePage(position);
        if(stateModel.getTotalPage()==0){
            titleToolBar.hideSupplyButton();
            notifyNoPage.setVisibility(View.VISIBLE);
        }
    }

    public void getPhoto(){
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .showCamera(true)
                .imageTitle("選擇補充照片")
                .single()
                .start(REQUEST_GETPHOTO);
    }
}

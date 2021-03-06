package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
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
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.NoteTitleDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteEditorActivity extends BaseActivity implements NoteEditorContract.View,NoteTitleDialogFragment.Callback,PageListAdapter.OnSelectItemListener
        ,OnImageClick,LinkMenuDialog.GetLinkNote{

    public static final String URL_KEY="NoteURL";
    private static final int REQUEST_GETPHOTO = 100;
    private static final int REQUEST_EDITOR=1111;

    TextView editor_btn;

    PageIndicator pageIndicator;
    TitleToolBar titleToolBar;
    PageManageWindow pageManageWindow;

    ViewPager content;

    boolean isPageEditorOpened=true;
    boolean isEditor=false;

    PageListAdapter pageListAdapter;

    NoteViewAdapter noteViewAdapter;

    TextView notifyNoPage;

    NoteEditorContract.Presenter presenter;

    PageStateModel stateModel;
    TitleDetailStateModel titleDetailStateModel;

    String noteUrl;

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

        Bundle bundle=this.getIntent().getExtras();
        if(bundle!=null) {
            noteUrl = bundle.getString(URL_KEY);
        }
        presenter=new NoteEidtorPresenter(this, new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()),new ImageRepository(FirebaseStorage.getInstance()),new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance()),stateModel,titleDetailStateModel);
        if(noteUrl==null){
            presenter.addNote();
        }else{
            presenter.loadData(noteUrl);
        }
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
    }

    private void initUI(){
        content=(ViewPager)findViewById(R.id.content);
        editor_btn=(TextView)findViewById(R.id.editor_btn);
        notifyNoPage=(TextView)findViewById(R.id.notifyNoPage);
        noteViewAdapter=new NoteViewAdapter(this,this);
        pageListAdapter=new PageListAdapter(this);
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
        pageManageWindow=new PageManageWindow((ViewGroup) findViewById(R.id.editor_layout), pageListAdapter,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPhoto();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLink();
                    }
                });

    }

    private void initAdapter(){
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
                        presenter.getTitleDetail(noteUrl);
                        Toast.makeText(v.getContext(),"click", Toast.LENGTH_SHORT).show();
                    }
                }
                ,new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.saveNote(noteUrl);
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
    public void shownotify() {
        titleToolBar.hideSupplyButton();
        notifyNoPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNoteId(String noteUrl) {
        this.noteUrl=noteUrl;
    }

    @Override
    public void end() {
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();

    }

    public void openPageEditor(){
        pageManageWindow.openWindow();
    }

    public void closePageEditor(){
        pageManageWindow.closeWindow();
    }

    public void openSupplyEdit(List<Supply> supplies){
        SupplyEditorFragment supplyEditorFragment = SupplyEditorFragment.newInstance(noteUrl,new SupplyStateModel(supplies));
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
                presenter.addPage(noteUrl,i.getPath());
            }

        }

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
        presenter.updateTitleDetail(noteUrl,title,color);
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
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("退出編輯")
                    .setMessage("點選確認保存筆記")
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.saveNote(noteUrl);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }else {
            super.onBackPressed();
        }
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

    public static void start(Context context, String noteUrl){
        Intent intent=new Intent();
        intent.setClass(context,NoteEditorActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static  void start(Activity activity,String noteUrl,int requestCode){
        Intent intent=new Intent();
        intent.setClass(activity,NoteEditorActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,requestCode);
    }

    public void getLink() {
        LinkMenuDialog dialog=LinkMenuDialog.newInstance(noteUrl,this);
        dialog.show(getSupportFragmentManager(),"LinkMenuDialog");
    }

    @Override
    public void onCallBack(Map<String, List<String>> notelist) {
        presenter.addLinkPage(noteUrl,notelist);
    }
}

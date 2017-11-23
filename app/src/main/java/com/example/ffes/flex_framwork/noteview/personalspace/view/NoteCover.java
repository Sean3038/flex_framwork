package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.dex.util.FileUtils;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.CoverAdapter;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Notebook;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

/**
 * Created by user on 2017/11/7.
 */

public class NoteCover extends BaseActivity implements CoverAdapter.OnCoverSelect{


    public static final int REQUEST_GETPHOTO=111;
    public static final String NOTE_URL_KEY="noteurl";
    DisplayMetrics mPhone;

    RecyclerView recyclerView;
    CoverAdapter adapter;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    EditText title;
    BlurView blurView;
    BlurView topBlurView;
    ImageView background;
    ImageView cover;
    //String CoverUri;

    ImageButton check;

    List<String> mData;

    String url;
    String coverurl;

    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notecover);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
        initUI();
        init();
        initPublicCover();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            url = bundle.getString(NOTE_URL_KEY);
            prepareNote();
        }
    }

    public static void startAction(Context context,String url) {
        Intent intent = new Intent();
        intent.setClass(context, NoteCover.class);
        Bundle bundle=new Bundle();
        bundle.putString(NOTE_URL_KEY,url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void newNoteBook(Fragment fragment, int requestcode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getContext(), NoteCover.class);
        fragment.startActivityForResult(intent,requestcode);

    }

    private void initUI(){
        recyclerView = (RecyclerView)findViewById(R.id.list_view);
        cover = (ImageView)findViewById(R.id.cover);
        title=(EditText) findViewById(R.id.title_content);
        background=(ImageView)findViewById(R.id.background);
        check = (ImageButton)findViewById(R.id.check);
        blurView = (BlurView)findViewById(R.id.blurView);
        topBlurView = (BlurView)findViewById(R.id.top);
    }

    private void init(){
        mPhone = new DisplayMetrics();
        mData = new ArrayList<>();
        adapter = new CoverAdapter(this,this, mData);

         //cover的背景要換成選擇的cardview圖片
         //root.setBackground()背景換成選擇的cardview圖片

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //儲存選擇的封面
                if((!(title.getText().toString().length()==0)) && coverurl !=null) {
                    showProgress("儲存筆記本資訊");
                    if (flag) {
                        StorageReference sef=FirebaseStorage.getInstance().getReferenceFromUrl(coverurl);
                        final long ONE_MEGABYTE=1024*1024;
                        sef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                if(url==null){
                                    noteRepository.addNoteBook(authRepository.getCurrentId(), title.getText().toString(), bytes, new OnUpLoadDataCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dismissProgress();
                                            finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            dismissProgress();
                                            finish();
                                        }
                                    });

                                }else{
                                    noteRepository.updateNoteBook(authRepository.getCurrentId(),url,title.getText().toString(),bytes, new OnUpLoadDataCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            dismissProgress();
                                            finish();
                                        }

                                        @Override
                                        public void onFailure() {
                                            dismissProgress();
                                            finish();
                                        }
                                    });
                                }
                            }
                        });
                    }else{
                        File file = new File(coverurl);
                        int size = (int) file.length();
                        byte[] b = new byte[size];
                        try {
                            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                            buf.read(b, 0, b.length);
                            buf.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(url==null){
                            noteRepository.addNoteBook(authRepository.getCurrentId(), title.getText().toString(), b, new OnUpLoadDataCallback<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dismissProgress();
                                    finish();
                                }

                                @Override
                                public void onFailure() {
                                    dismissProgress();
                                    finish();
                                }
                            });

                        }else{
                            noteRepository.updateNoteBook(authRepository.getCurrentId(),url,title.getText().toString(),b, new OnUpLoadDataCallback<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dismissProgress();
                                    finish();
                                }

                                @Override
                                public void onFailure() {
                                    dismissProgress();
                                    finish();
                                }
                            });
                        }
                    }
                }else{
                    new AlertDialog.Builder(NoteCover.this).setMessage("請選擇照片與輸入標題")
                            .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setResult(RESULT_CANCELED);
                                    finish();
                                }
                            }).show();
                }

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CoverAdapter(this,this, mData);
        recyclerView.setAdapter(adapter);

        final float radius = 20;
        final View decorView = getWindow().getDecorView();
        final ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        final Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this))
                .blurRadius(radius);
        topBlurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(new RenderScriptBlur(this))
                .blurRadius(radius);
    }


    private void prepareNote() {
        showProgress("讀取資料...");
        noteRepository.getNoteBook(authRepository.getCurrentId(), url, new OnGetDataCallBack<Notebook>() {
            @Override
            public void onSuccess(Notebook data) {
                title.setText(data.getTitle());
                coverurl=data.getCover();
                Picasso.with(NoteCover.this).load(data.getCover()).centerInside().fit().into(NoteCover.this.cover);
                Picasso.with(NoteCover.this).load(data.getCover()).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE).fit().into(background);
                dismissProgress();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_GETPHOTO:
                if(resultCode==RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    for(Image i:images){
                        flag=false;
                        File f=new File(i.getPath());
                        Picasso.with(NoteCover.this).load(f).centerInside().fit().into(NoteCover.this.cover);
                        Picasso.with(NoteCover.this).load(f).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE).fit().into(background);
                        coverurl=i.getPath();
                    }
                }
                break;
        }
    }

    public void initPublicCover(){
        noteRepository.getPublicCover(new OnGetDataCallBack<List<String>>() {
            @Override
            public void onSuccess(List<String> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onAddCover() {
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .showCamera(true)
                .imageTitle("選擇補充照片")
                .single()
                .start(REQUEST_GETPHOTO);
    }

    @Override
    public void onSelectCover(String cover) {
        flag=true;
        Picasso.with(this).load(cover).centerInside().fit().into(this.cover);
        Picasso.with(this).load(cover).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_CACHE).fit().into(background);
        coverurl=cover;
    }
}

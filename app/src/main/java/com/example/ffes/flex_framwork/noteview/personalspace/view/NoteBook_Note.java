package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.NoteBookNoteAdapter;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Notebook;
import com.example.ffes.flex_framwork.noteview.widget.ShareNoteDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import static com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity.URL_KEY;


/**
 * Created by user on 2017/11/8.
 */

public class NoteBook_Note extends BaseActivity implements ShareNoteDialog.OnClickShare,NoteBookNoteAdapter.OnAddNote{

    public static final String NOTEBOOK_URL_KEY="bookurl";
    public static final int REQUEST_ADD_NOTE=100;

    TextView notebookname;
    RecyclerView recyclerView;
    NoteBookNoteAdapter adapter;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    String bookurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_note);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());

        Intent intent=getIntent();
        bookurl=intent.getExtras().getString(NOTEBOOK_URL_KEY);

        initUI();
        init();
        prepareNote();
    }
    private void initUI(){
        recyclerView = (RecyclerView)findViewById(R.id.list_view);
        notebookname = (TextView)findViewById(R.id.NoteBookName);
    }
    private void init(){
        adapter=new NoteBookNoteAdapter(this,this,getSupportFragmentManager(),bookurl);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
    private void prepareNote(){

        noteRepository.getNoteBook(authRepository.getCurrentId(), bookurl, new OnGetDataCallBack<Notebook>() {

            @Override
            public void onSuccess(Notebook data) {
                notebookname.setText(data.getTitle());
            }

            @Override
            public void onFailure() {

            }
        });

        noteRepository.getPersonalNoteFromNotebook(authRepository.getCurrentId(),bookurl, new OnGetDataCallBack<List<PersonalNote>>() {
            @Override
            public void onSuccess(List<PersonalNote> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });

    }

    public static void startAction(Context context,String bookurl){
        Intent intent = new Intent();
        intent.setClass(context, NoteBook_Note.class);
        Bundle bundle=new Bundle();
        bundle.putString(NOTEBOOK_URL_KEY,bookurl);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareNote();
    }

    @Override
    public void onShare(String noteurl, String college, String dep) {
        if(college!=null && dep!=null) {
            showProgress("分享中.....");
            noteRepository.share(authRepository.getCurrentId(), noteurl, college, dep, new OnUpLoadDataCallback() {
                @Override
                public void onSuccess(Object o) {
                    dismissProgress();
                }

                @Override
                public void onFailure() {
                    dismissProgress();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_ADD_NOTE:
                if(resultCode==RESULT_OK){
                    String noteurl=data.getExtras().getString(URL_KEY);
                    noteRepository.addNoteToNoteBook(authRepository.getCurrentId(),noteurl,bookurl);
                    prepareNote();
                }
                break;
        }
    }

    @Override
    public void onAdd() {
        Toast.makeText(this,"筆記本空間", Toast.LENGTH_SHORT).show();
        new AlertDialog.Builder(NoteBook_Note.this).setMessage("請選增加類型")
                .setPositiveButton("從個人空間匯入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SelectPersonalNote.start(NoteBook_Note.this,bookurl);
                    }
                })
                .setNegativeButton("新增筆記", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NoteEditorActivity.start(NoteBook_Note.this,null,REQUEST_ADD_NOTE);
                    }
                }).show();

    }
}

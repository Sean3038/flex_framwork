package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SelectPersonalNote extends AppCompatActivity {
    public static final String NOTEBOKK_URL_KEY="notebook";
    AuthRepository authRepository;
    NoteRepository noteRepository;
    SelectNoteAdapter adapter;
    RecyclerView recyclerView;
    TextView confirm;

    String bookurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personal_note);
        recyclerView=(RecyclerView)findViewById(R.id.list_view);
        confirm=(TextView)findViewById(R.id.confirm);
        authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        bookurl=getIntent().getExtras().getString(NOTEBOKK_URL_KEY);

        initAdapter();
        initNote();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteToNoteBook();
                finish();
            }
        });
    }

    private void initAdapter(){
        adapter=new SelectNoteAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private  void initNote(){
        noteRepository.getPersonalSpaceOtherNoteData(authRepository.getCurrentId(),bookurl, new OnGetDataCallBack<List<PersonalNote>>() {
            @Override
            public void onSuccess(List<PersonalNote> data) {
                adapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void addNoteToNoteBook(){
        List<PersonalNote> notes=adapter.getSelectedItem();
        List<String> ids=new ArrayList<>();
        for(PersonalNote note:notes){
            ids.add(note.getNoteURL());
        }
        noteRepository.addNoteToNoteBook(authRepository.getCurrentId(),bookurl,ids);
    }

    public static void start(Context context,String bookurl) {
        Intent starter = new Intent(context, SelectPersonalNote.class);
        Bundle bundle=new Bundle();
        bundle.putString(NOTEBOKK_URL_KEY,bookurl);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }
}

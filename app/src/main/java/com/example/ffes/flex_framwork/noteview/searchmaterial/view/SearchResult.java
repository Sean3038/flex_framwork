package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.NoteAdapter;
import com.example.ffes.flex_framwork.noteview.searchmaterial.adapter.AlertDialogAdapter;
import com.example.ffes.flex_framwork.noteview.searchmaterial.data.Dep;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity implements View.OnClickListener ,AlertDialogAdapter.OnClickDep{

    public static final String COLLEGE_KEY="college";
    public static final String DEP_KEY="dep";
    public static final String KEY_KEY="key";
    TextView all;
    TextView college;
    TextView college1;
    TextView college2;
    TextView college3;

    SearchView searchView;

    ArrayList<Dep> mData;
    AlertDialogAdapter adapter;
    RecyclerView recyclerView;

    RecyclerView notelist;
    NoteRepository noteRepository;
    NoteAdapter noteAdapter;

    String currentcollege="";
    String currentdep="";
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        if(getIntent().getExtras()!=null){
            currentcollege=getIntent().getExtras().getString(COLLEGE_KEY);
            currentdep=getIntent().getExtras().getString(DEP_KEY);
            key=getIntent().getExtras().getString(KEY_KEY);
            Log.d("DEP",currentcollege +currentdep);
        }
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        initUI();
        init();
        prepareDep();
        initnotelist();
    }

    private void initUI() {
        all = (TextView) findViewById(R.id.all);
        college = (TextView) findViewById(R.id.college);
        college1 = (TextView) findViewById(R.id.college1);
        college2 = (TextView) findViewById(R.id.college2);
        college3 = (TextView) findViewById(R.id.college3);
        searchView = (SearchView) findViewById(R.id.search_view);
        notelist=(RecyclerView)findViewById(R.id.notelist);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key=query;
                search(currentcollege,currentdep,key);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void init() {
        all.setOnClickListener(this);
        college.setOnClickListener(this);
        college1.setOnClickListener(this);
        college2.setOnClickListener(this);
        college3.setOnClickListener(this);
        //number.setText();抓底下的筆記本數量
    }

    private void prepareDep() {
        mData = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AlertDialogAdapter(this, mData,this);
        recyclerView.setAdapter(adapter);
    }

    private void initnotelist(){
        noteAdapter=new NoteAdapter(this,new ArrayList<SharedNote>());
        notelist.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        notelist.setAdapter(noteAdapter);
        search(currentcollege,currentdep,key);
    }

    @Override
    public void onClick(View v) {
        all.setTextColor(Color.parseColor("#8ba7cb"));
        all.setPaintFlags(0);
        college.setTextColor(Color.parseColor("#8ba7cb"));
        college.setPaintFlags(0);
        college1.setTextColor(Color.parseColor("#8ba7cb"));
        college1.setPaintFlags(0);
        college2.setTextColor(Color.parseColor("#8ba7cb"));
        college2.setPaintFlags(0);
        college3.setTextColor(Color.parseColor("#8ba7cb"));
        college3.setPaintFlags(0);

        currentcollege="";
        currentdep="";
        key="";

        switch (v.getId()) {
            case R.id.all:
                all.setTextColor(Color.parseColor("#013270"));
                all.setPaintFlags(all.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mData.clear();
                break;
            case R.id.college:
                college.setTextColor(Color.parseColor("#013270"));
                college.setPaintFlags(college.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mData.clear();
                currentcollege="管理";
                Dep a = new Dep("財務金融系", false);
                mData.add(a);
                a = new Dep("工管系", false);
                mData.add(a);
                a = new Dep("會計系", false);
                mData.add(a);
                a = new Dep("資訊管理系", false);
                mData.add(a);
                a = new Dep("企業管理系", false);
                mData.add(a);
                adapter.notifyDataSetChanged();
                break;
            case R.id.college1:
                college1.setTextColor(Color.parseColor("#013270"));
                college1.setPaintFlags(college1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                currentcollege="工程";
                mData.clear();
                a = new Dep("機械工程系", false);
                mData.add(a);
                a = new Dep("電機工程系", false);
                mData.add(a);
                a = new Dep("電子工程系", false);
                mData.add(a);
                a = new Dep("環安系", false);
                mData.add(a);
                a = new Dep("化材系", false);
                mData.add(a);
                a = new Dep("營建工程系", false);
                mData.add(a);
                a = new Dep("資訊工程系", false);
                mData.add(a);
                adapter.notifyDataSetChanged();
                break;
            case R.id.college2:
                college2.setTextColor(Color.parseColor("#013270"));
                college2.setPaintFlags(college2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mData.clear();
                currentcollege="人文";
                a = new Dep("文化資產維護系", false);
                mData.add(a);
                a = new Dep("應用外語系", false);
                mData.add(a);
                adapter.notifyDataSetChanged();
                break;
            case R.id.college3:
                college3.setTextColor(Color.parseColor("#013270"));
                college3.setPaintFlags(college3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                mData.clear();
                currentcollege="設計";
                a = new Dep("工業設計系", false);
                mData.add(a);
                a = new Dep("視覺傳達設計系", false);
                mData.add(a);
                a = new Dep("建築與室內設計系", false);
                mData.add(a);
                a = new Dep("數位媒體設計系", false);
                mData.add(a);
                a = new Dep("創意生活設計系", false);
                mData.add(a);
                adapter.notifyDataSetChanged();
                break;
        }
        search(currentcollege,currentdep,key);
    }

    public static void startAction(Context context,String college,String dep,String key) {//查詢後的結果會到這個頁面
        Intent intent = new Intent();
        intent.setClass(context, SearchResult.class);
        Bundle bundle=new Bundle();
        bundle.putString(COLLEGE_KEY,college);
        bundle.putString(DEP_KEY,dep);
        bundle.putString(KEY_KEY,key);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void search(String college,String dep,String key){
        noteAdapter.update(new ArrayList<SharedNote>());
        noteRepository.searchSharedNote(college, dep, key, new OnGetDataCallBack<List<SharedNote>>() {
            @Override
            public void onSuccess(List<SharedNote> data) {
                noteAdapter.update(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onClick(String dep) {
        currentdep=dep;
        key="";
        search(currentcollege,currentdep,key);
    }
}

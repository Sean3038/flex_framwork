package com.example.ffes.flex_framwork.noteview.api;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteBrowserModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteRepository implements NoteLoadModel,NoteBrowserModel{

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    public  NoteRepository(FirebaseDatabase firebaseDatabase,FirebaseStorage firebaseStorage){
        this.firebaseDatabase=firebaseDatabase;
        this.firebaseStorage=firebaseStorage;
    }

    @Override
    public void getPages(final String url, final OnGetDataCallBack<List<Page>> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/notecontent/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Page> pages=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Page page=new Page();
                    page.setId(child.child("id").getValue(String.class));
                    page.setimageurl(child.child("imageurl").getValue(String.class));

                    if(child.hasChild("keywordlist")){
                        DataSnapshot keywordchild=child.child("keywordlist");
                        GenericTypeIndicator<Map<String,KeyWord>> t=new GenericTypeIndicator<Map<String,KeyWord>>() {};
                        Map<String,KeyWord> keyWordList=keywordchild.getValue(t);
                        if(keyWordList==null){
                            keyWordList=new HashMap<>();
                        }
                        page.setkeywordlist(keyWordList);
                    }else{
                        page.setkeywordlist(new HashMap<String, KeyWord>());
                    }

                    if(child.hasChild("supplylist")){
                        DataSnapshot supplylistchild=child.child("supplylist");
                        List<Supply> supplyList=new ArrayList<>();
                        for(DataSnapshot supplychild:supplylistchild.getChildren()){
                            supplyList.add(supplychild.getValue(Supply.class));
                        }
                        page.setsupplylist(supplyList);
                    }else{
                        page.setsupplylist(new ArrayList<Supply>());
                    }

                    if(child.hasChild("qalist")){
                        DataSnapshot qalistchild=child.child("qalist");
                        List<QA> qaList=new ArrayList<>();
                        for(DataSnapshot qachild:qalistchild.getChildren()){
                            qaList.add(qachild.getValue(QA.class));
                        }
                        page.setqalist(qaList);
                    }else{
                        page.setqalist(new ArrayList<QA>());
                    }
                    pages.add(page);
                }
                callBack.onSuccess(pages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    @Override
    public void getNoteName(final String noteurl, final OnUpLoadDataCallback<String> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/titledetail/title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callBack.onSuccess(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    @Override
    public void getKeyList(String url, OnGetDataCallBack<List<String>> callBack) {

    }

    @Override
    public void getTitleDetail(String url, final OnGetDataCallBack<TitleDetail> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/titledetail").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TitleDetail titleDetail=dataSnapshot.getValue(TitleDetail.class);
                callBack.onSuccess(titleDetail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void updateTitleDetial(String url, TitleDetail titleDetail, final OnUpLoadDataCallback callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/titledetail/").setValue(titleDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        });
    }

    @Override
    public void addPage(final String url, final Page page, final OnUpLoadDataCallback callback) {
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/notecontent/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c=0;
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    if(c<Integer.parseInt(child.getKey())) {
                        c = Integer.parseInt(child.getKey());
                    }
                }

                final String uid=ref.child("note/"+url+"/notecontent/"+c).push().getKey();
                page.setId(uid);
                ref.child("note/"+url+"/notecontent/"+c+uid).setValue(page).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(null);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void updateNoteContent(final String url, Map<String,Object> p, final OnUpLoadDataCallback callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/notecontent/").setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        });
    }

    public void updatePage(String url, Page page, final OnUpLoadDataCallback callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/notecontent/"+page.getId()).setValue(page.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        });
    }

    public void deletePage(String url,String pageurl,boolean isLink,OnUpLoadDataCallback callback){

    }

}

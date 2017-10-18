package com.example.ffes.flex_framwork.noteview.NoteBrowser.model;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyEditorModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteBrowserModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageContentModel;
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

public class NoteRepository implements KeyEditorModel ,PageContentModel,NoteLoadModel,NoteBrowserModel{

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    public  NoteRepository(FirebaseDatabase firebaseDatabase,FirebaseStorage firebaseStorage){
        this.firebaseDatabase=firebaseDatabase;
        this.firebaseStorage=firebaseStorage;
    }

    public void getPage(final String url, final String pageurl, final OnGetDataCallBack<Page> callback){
        DatabaseReference ncref=firebaseDatabase.getReference();
        ncref.child("note/"+url+"/notecontent/"+pageurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Page page=new Page();
                final int[] count = {0};
                page.setimageurl(dataSnapshot.child("imageurl").getValue(String.class));
                page.setId(dataSnapshot.child("id").getValue(String.class));
                getKeyWord(url, pageurl, new OnGetDataCallBack<Map<String,KeyWord>>() {
                    @Override
                    public void onSuccess(Map<String,KeyWord> data) {
                        page.setkeywordlist(data);
                        count[0]++;
                        if(count[0]==3){
                            callback.onSuccess(page);
                        }
                    }

                    @Override
                    public void onFailure() {
                        callback.onFailure();
                    }
                });
                getSupply(url, pageurl, new OnGetDataCallBack<List<Supply>>() {
                    @Override
                    public void onSuccess(List<Supply> data) {
                        page.setsupplylist(data);
                        count[0]++;
                        if(count[0]==3){
                            callback.onSuccess(page);
                        }

                    }

                    @Override
                    public void onFailure() {
                        callback.onFailure();
                    }
                });
                getQAList(url, pageurl, new OnGetDataCallBack<List<QA>>() {
                    @Override
                    public void onSuccess(List<QA> data) {
                        page.setqalist(data);
                        count[0]++;
                        if(count[0]==3){
                            callback.onSuccess(page);
                        }
                    }

                    @Override
                    public void onFailure() {
                        callback.onFailure();
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    public void getSupply(String url, String pageurl, final OnGetDataCallBack<List<Supply>> callBack){
        DatabaseReference ncref=firebaseDatabase.getReference();
        ncref.child("note/"+url+"/notecontent/"+pageurl+"/supplylist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Supply> supplyList=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    supplyList.add(child.getValue(Supply.class));
                }
                callBack.onSuccess(supplyList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getKeyWord(String url, String pageurl, final OnGetDataCallBack<Map<String,KeyWord>> callBack){
        DatabaseReference ncref=firebaseDatabase.getReference();
        ncref.child("note/"+url+"/notecontent/"+pageurl+"/keywordlist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String,KeyWord>> t=new GenericTypeIndicator<Map<String,KeyWord>>() {};
                Map<String,KeyWord> keyWordList=dataSnapshot.getValue(t);
                if(keyWordList==null){
                    keyWordList=new HashMap<>();
                }
                callBack.onSuccess(keyWordList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getQAList(String url, String pageurl, final OnGetDataCallBack<List<QA>> callBack){
        DatabaseReference ncref=firebaseDatabase.getReference();
        ncref.child("note/"+url+"/notecontent/"+pageurl+"/qalist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<QA> qaList=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    qaList.add(child.getValue(QA.class));
                }
                callBack.onSuccess(qaList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    @Override
    public void getShowPages(String url, final OnGetDataCallBack<List<Integer>> callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/pagelist").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Integer> pages=new ArrayList<>();
                for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    pages.add(Integer.valueOf(childSnapshot.getKey()));
                }
                callback.onSuccess(pages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getPageContent(final String url, int page, final OnGetDataCallBack<Page> callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/pagelist/"+page).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pageurl=dataSnapshot.getValue(String.class);
                getPage(url,pageurl,callback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getPages(final String url, final OnGetDataCallBack<Page> callBack) {
        getShowPages(url, new OnGetDataCallBack<List<Integer>>() {

            @Override
            public void onSuccess(List<Integer> data) {
                for(Integer i:data){
                    getPageContent(url, i,callBack);
                }

            }

            @Override
            public void onFailure() {

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
    public void addPage(final String url, Page page, final OnUpLoadDataCallback callback) {
        addPageContent(url, page, new OnUpLoadDataCallback<String>() {

            @Override
            public void onSuccess(final String s) {
                DatabaseReference ref=firebaseDatabase.getReference();
                ref.child("note/"+url+"/pagelist/").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DatabaseReference nref=firebaseDatabase.getReference();
                        long c=dataSnapshot.getChildrenCount()+1;
                        nref.child("note/"+url+"/pagelist/"+c).setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callback.onSuccess(null);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void updateNoteContent(final String url, Map<String,Object> p, final TitleDetail titleDetail, final OnUpLoadDataCallback callback) {
        Log.d("MAP",p.toString());
        updatePageContents(url, p, new OnUpLoadDataCallback<String>() {
            @Override
            public void onSuccess(String s) {
                updateTitleDetial(url, titleDetail, new OnUpLoadDataCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        callback.onSuccess(null);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void updatePageContents(String url, Map<String,Object>  page, final OnUpLoadDataCallback<String> callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        Log.d("map",page.toString());
        ref.child("note/"+url+"/notecontent/").setValue(page).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        });
    }

    public void addPageContent(String url, Page page, final OnUpLoadDataCallback<String> callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        final String uid=ref.child("note/"+url+"/notecontent/").push().getKey();
        page.setId(uid);
        ref.child("note/"+url+"/notecontent/"+uid).setValue(page).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(uid);
            }
        });
    }

    @Override
    public void updatePageLink(String url, Map<String,Object> link, final OnUpLoadDataCallback callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/pagelist/").setValue(link).addOnSuccessListener(new OnSuccessListener<Void>() {
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

}

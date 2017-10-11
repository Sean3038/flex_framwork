package com.example.ffes.flex_framwork.noteview.NoteBrowser.model;

import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.KeyEditorModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.PageContentModel;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteRepository implements KeyEditorModel ,PageContentModel,NoteLoadModel{

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    public  NoteRepository(FirebaseDatabase firebaseDatabase,FirebaseStorage firebaseStorage){
//        data=new Note("dsaf", Arrays.asList(
//                new Page("http://i.imgur.com/DvpvklR.png",Arrays.asList(new Supply(1,1,"I'm data")),Arrays.asList(new KeyWord("Strategy",new RectF(0,0,200,200),Color.parseColor("#cc66ee99")),new KeyWord("Memento",new RectF(250,250,400,400), Color.parseColor("#ccffeeee")),new KeyWord("Observer",new RectF(600,600,900,900),Color.parseColor("#cc00eeff")),new KeyWord("Iterator",new RectF(0,0,60,60), Color.parseColor("#cc55eeee"))),null),
//                new Page("https://i.imgur.com/tGbaZCY.jpg",Arrays.asList(new Supply(1,1,"I'm data2"),new Supply(2,2,"https://i.imgur.com/ME4ewjl.jpg"),new Supply(3,2,"https://i.imgur.com/tGbaZCY.jpg"),new Supply(4,1,"this is test 3")),Arrays.asList(new KeyWord("State",new RectF(300,250,600,700),Color.parseColor("#aa000eff"))),null)
//        ),"coverurl","Pattern","#f6b9b9","authorID","20171029","20170648");
        this.firebaseDatabase=firebaseDatabase;
        this.firebaseStorage=firebaseStorage;
    }

    public void getPage(String url, String pageurl, final OnGetDataCallBack<Page> callback){
        DatabaseReference ncref=firebaseDatabase.getReference();
        ncref.child("note/"+url+"/notecontent/"+pageurl).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Page page=dataSnapshot.getValue(Page.class);
                callback.onSuccess(page);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    @Override
    public void getShowPages(String url, final OnGetDataCallBack<List<Integer>> callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+url+"/pagelist").addValueEventListener(new ValueEventListener() {

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
    public void updateTitleDetial(String url, String title, String color, final OnUpLoadDataCallback callback) {
        DatabaseReference ref=firebaseDatabase.getReference();
        TitleDetail titleDetail=new TitleDetail(color,title);
        ref.child("note/"+url+"/titledetail").setValue(titleDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        });
    }

}

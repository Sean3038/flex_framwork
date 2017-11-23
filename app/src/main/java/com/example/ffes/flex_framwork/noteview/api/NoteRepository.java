package com.example.ffes.flex_framwork.noteview.api;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.LinkNote;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.data.TitleDetail;
import com.example.ffes.flex_framwork.noteview.linknote.data.Message;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Notebook;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.ffes.flex_framwork.R.id.cancel_action;
import static com.example.ffes.flex_framwork.R.id.child;
import static com.example.ffes.flex_framwork.R.id.keylist;

/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteRepository{

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;

    public  NoteRepository(FirebaseDatabase firebaseDatabase,FirebaseStorage firebaseStorage){
        this.firebaseDatabase=firebaseDatabase;
        this.firebaseStorage=firebaseStorage;
    }

    public void getPages(String uid,final String url, final OnGetDataCallBack<List<Page>> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url+"/notecontent/").addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getNoteName(String uid,final String noteurl, final OnUpLoadDataCallback<String> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+noteurl+"/title").addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getNoteCover(String uid,final String noteurl, final OnGetDataCallBack<String> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+noteurl+"/notecontent/0/imageurl").addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getPersonalPageByKeyWord(String uid, final String noteurl, final List<String> keylist, final OnGetDataCallBack<List<Page>> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/").orderByKey().equalTo(noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Page> pages=new ArrayList<>();
                for(DataSnapshot pageSnapshot:dataSnapshot.child(noteurl+"/notecontent/").getChildren()){
                    for(DataSnapshot keySnapshot:pageSnapshot.child("keywordlist").getChildren()){
                        if(keylist.contains(keySnapshot.getKey())){
                            //加入這頁筆記
                            Page page=new Page();
                            page.setId(pageSnapshot.child("id").getValue(String.class));
                            page.setimageurl(pageSnapshot.child("imageurl").getValue(String.class));

                            if(pageSnapshot.hasChild("keywordlist")){
                                DataSnapshot keywordchild=pageSnapshot.child("keywordlist");
                                GenericTypeIndicator<Map<String,KeyWord>> t=new GenericTypeIndicator<Map<String,KeyWord>>() {};
                                Map<String,KeyWord> keyWordList=keywordchild.getValue(t);
                                if(keyWordList==null){
                                    keyWordList=new HashMap<>();
                                }
                                page.setkeywordlist(keyWordList);
                            }else{
                                page.setkeywordlist(new HashMap<String, KeyWord>());
                            }
                            if(pageSnapshot.hasChild("supplylist")){
                                DataSnapshot supplylistchild=pageSnapshot.child("supplylist");
                                List<Supply> supplyList=new ArrayList<>();
                                for(DataSnapshot supplychild:supplylistchild.getChildren()){
                                    supplyList.add(supplychild.getValue(Supply.class));
                                }
                                page.setsupplylist(supplyList);
                            }else{
                                page.setsupplylist(new ArrayList<Supply>());
                            }
                            if(pageSnapshot.hasChild("qalist")){
                                DataSnapshot qalistchild=pageSnapshot.child("qalist");
                                List<QA> qaList=new ArrayList<>();
                                for(DataSnapshot qachild:qalistchild.getChildren()){
                                    qaList.add(qachild.getValue(QA.class));
                                }
                                page.setqalist(qaList);
                            }else{
                                page.setqalist(new ArrayList<QA>());
                            }
                            pages.add(page);
                            Log.d("PAGE",page.getId());
                            break;
                        }
                    }
                }
                callBack.onSuccess(pages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getLinkPageByKeyWord(final String noteurl, final List<String> keylist, final OnGetDataCallBack<List<Page>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/authorid").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ref.child("user/"+dataSnapshot.getValue(String.class)+"/personalspace/").orderByKey().equalTo(noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Page> pages=new ArrayList<>();
                        for(DataSnapshot pageSnapshot:dataSnapshot.child(noteurl+"/notecontent/").getChildren()){
                            for(DataSnapshot keySnapshot:pageSnapshot.child("keywordlist").getChildren()){
                                if(keylist.contains(keySnapshot.getKey())){
                                    //加入這頁筆記
                                    Page page=new Page();
                                    page.setId(pageSnapshot.child("id").getValue(String.class));
                                    page.setimageurl(pageSnapshot.child("imageurl").getValue(String.class));

                                    if(pageSnapshot.hasChild("keywordlist")){
                                        DataSnapshot keywordchild=pageSnapshot.child("keywordlist");
                                        GenericTypeIndicator<Map<String,KeyWord>> t=new GenericTypeIndicator<Map<String,KeyWord>>() {};
                                        Map<String,KeyWord> keyWordList=keywordchild.getValue(t);
                                        if(keyWordList==null){
                                            keyWordList=new HashMap<>();
                                        }
                                        page.setkeywordlist(keyWordList);
                                    }else{
                                        page.setkeywordlist(new HashMap<String, KeyWord>());
                                    }
                                    if(pageSnapshot.hasChild("supplylist")){
                                        DataSnapshot supplylistchild=pageSnapshot.child("supplylist");
                                        List<Supply> supplyList=new ArrayList<>();
                                        for(DataSnapshot supplychild:supplylistchild.getChildren()){
                                            supplyList.add(supplychild.getValue(Supply.class));
                                        }
                                        page.setsupplylist(supplyList);
                                    }else{
                                        page.setsupplylist(new ArrayList<Supply>());
                                    }
                                    if(pageSnapshot.hasChild("qalist")){
                                        DataSnapshot qalistchild=pageSnapshot.child("qalist");
                                        List<QA> qaList=new ArrayList<>();
                                        for(DataSnapshot qachild:qalistchild.getChildren()){
                                            qaList.add(qachild.getValue(QA.class));
                                        }
                                        page.setqalist(qaList);
                                    }else{
                                        page.setqalist(new ArrayList<QA>());
                                    }
                                    pages.add(page);
                                    Log.d("PAGE",page.getId());
                                    break;
                                }
                            }
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPersonalSpaceAllNoteData(String uid, final OnGetDataCallBack<List<PersonalNote>> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PersonalNote> list=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    PersonalNote data=new PersonalNote();
                    data.setCoverurl(child.child("notecontent/0/imageurl").getValue(String.class));
                    data.setColor(child.child("color").getValue(String.class));
                    data.setNoteURL(child.getKey());
                    data.setShare(child.child("isShare").getValue(Boolean.class));
                    data.setUid(child.child("authorid").getValue(String.class));
                    data.setTitle(child.child("title").getValue(String.class));
                    list.add(data);
                }
                callBack.onSuccess(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getPersonalSpaceOtherNoteData(final String uid, String bookurl, final OnGetDataCallBack<List<PersonalNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl+"/note").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> current=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    current.add(child.getValue(String.class));
                }
                ref.child("user/"+uid+"/personalspace/").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<PersonalNote> list=new ArrayList<>();
                        for(DataSnapshot child:dataSnapshot.getChildren()){
                            if(!current.contains(child.getKey())) {
                                PersonalNote data = new PersonalNote();
                                data.setCoverurl(child.child("notecontent/0/imageurl").getValue(String.class));
                                data.setColor(child.child("color").getValue(String.class));
                                data.setNoteURL(child.getKey());
                                data.setShare(child.child("isShare").getValue(Boolean.class));
                                data.setUid(child.child("authorid").getValue(String.class));
                                data.setTitle(child.child("title").getValue(String.class));
                                list.add(data);
                            }
                        }
                        callBack.onSuccess(list);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callBack.onFailure();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getTitleDetail(String uid,String url, final OnGetDataCallBack<TitleDetail> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TitleDetail titleDetail=new TitleDetail();
                titleDetail.setTitle(dataSnapshot.child("title").getValue(String.class));
                titleDetail.setColor(dataSnapshot.child("color").getValue(String.class));
                callBack.onSuccess(titleDetail);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getLinkSpaceNote(final String uid, final String url, final OnGetDataCallBack<List<LinkNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/linkspace/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<LinkNote> linkNotes=new ArrayList<>();
                final int[] c = {0};
                final int total=(int)dataSnapshot.getChildrenCount();
                //連結的url
                for(final DataSnapshot childSnapshot:dataSnapshot.getChildren()){
                    final String linkurl=childSnapshot.getValue(String.class);
                    ref.child("note/").orderByKey().equalTo(linkurl).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            boolean flag=dataSnapshot.hasChild(linkurl);
                            if(flag){
                                LinkNote linkNote=new LinkNote();
                                linkNote.setTitle(dataSnapshot.child(linkurl+"/title").getValue(String.class));
                                linkNote.setName(dataSnapshot.child(linkurl+"/authorid").getValue(String.class));
                                linkNote.setId(linkurl);
                                List<String> keys=new ArrayList<>();
                                for(DataSnapshot keychild :dataSnapshot.child(linkurl+"/keylist").getChildren()){
                                    keys.add(keychild.getValue(String.class));
                                }
                                linkNote.setKeylsit(keys);
                                linkNotes.add(linkNote);
                            }else{
                                ref.child("user/"+uid+"/linkspace/"+childSnapshot.getKey()).removeValue();
                            }
                            c[0]++;
                            if(c[0]==total){
                                callBack.onSuccess(linkNotes);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callBack.onFailure();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();;
            }
        });
    }

    public void getSelfSharedNote(final String uid, final OnGetDataCallBack<List<SharedNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/").orderByChild("isShare").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<SharedNote> notes=new ArrayList<>();
                final int total=(int)dataSnapshot.getChildrenCount();
                final int[] c = {0};
                for(final DataSnapshot child:dataSnapshot.getChildren()){
                    ref.child("note/").orderByKey().equalTo(child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //平台上的筆記
                            final SharedNote note=new SharedNote();
                            final int[] i = {0};
                            final DataSnapshot value=dataSnapshot.child(child.getKey());
                            note.setLike((int)value.child("like").getChildrenCount());
                            note.setComment((int)value.child("message").getChildrenCount());
                            note.setLook((int)value.child("look").getChildrenCount());
                            note.setLink((int)value.child("link").getChildrenCount());
                            note.setId(value.getKey());
                            note.setUid(value.child("authorid").getValue(String.class));

                            //尋找筆記資料
                            ref.child("user/"+uid+"/personalspace/"+child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                    note.setTitle(dataSnapshot.child("title").getValue(String.class));

                                    ref.child("account/"+value.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            note.setName(dataSnapshot.child("name").getValue(String.class));
                                            note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                            notes.add(note);
                                            c[0]++;
                                            if(c[0]==total){
                                                callBack.onSuccess(notes);
                                                Log.d("UID",""+c[0]);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPersonalNoteByKey(String uid, final String key, final OnGetDataCallBack<List<PersonalNote>> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace").orderByChild("title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PersonalNote> personalNotes=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    String title=child.child("title").getValue(String.class);
                    List<String> keylist=new ArrayList<>();
                    boolean flag=false;
                    for(DataSnapshot key:child.child("keylist").getChildren()){
                        keylist.add(key.getValue(String.class));
                    }

                    if (title.contains(key) || keylist.contains(key)){
                        flag=true;
                    }
                    if(!flag) {
                        for (String keyword : keylist) {
                            if(keyword.contains(key)){
                                flag=true;
                                break;
                            }
                        }
                    }
                    if(flag){
                        PersonalNote data=new PersonalNote();
                        data.setCoverurl(child.child("notecontent/0/imageurl").getValue(String.class));
                        data.setColor(child.child("color").getValue(String.class));
                        data.setNoteURL(child.getKey());
                        data.setShare(child.child("isShare").getValue(Boolean.class));
                        data.setUid(child.child("authorid").getValue(String.class));
                        data.setTitle(child.child("title").getValue(String.class));
                        personalNotes.add(data);
                    }
                }
                callBack.onSuccess(personalNotes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getShareNote(String noteurl, final OnGetDataCallBack<SharedNote> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final SharedNote note = new SharedNote();
                note.setLike((int) dataSnapshot.child("like").getChildrenCount());
                note.setComment((int) dataSnapshot.child("message").getChildrenCount());
                note.setLook((int) dataSnapshot.child("look").getChildrenCount());
                note.setLink((int) dataSnapshot.child("link").getChildrenCount());
                note.setId(dataSnapshot.getKey());
                note.setUid(dataSnapshot.child("authorid").getValue(String.class));
                //尋找筆記資料
                ref.child("user/" + dataSnapshot.child("authorid").getValue(String.class) + "/personalspace/" + dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                        note.setTitle(dataSnapshot.child("title").getValue(String.class));
                        ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                note.setName(dataSnapshot.child("name").getValue(String.class));
                                note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                callBack.onSuccess(note);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkLike(final String uid, final String noteurl, final OnGetDataCallBack<Boolean> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/" + noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot child : dataSnapshot.child("like").getChildren()) {
                    if (uid.equals(child.getValue(String.class))) {
                        flag=true;
                        break;
                    }
                }
                callBack.onSuccess(flag);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkLink(final String uid, final String noteurl, final OnGetDataCallBack<Boolean> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/" + noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag = false;
                for (DataSnapshot child : dataSnapshot.child("link").getChildren()) {
                    if (uid.equals(child.getValue(String.class))) {
                        flag=true;
                        break;
                    }
                }
                callBack.onSuccess(flag);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSharedNoteFromSpace(final String uid, final OnGetDataCallBack<List<SharedNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/linkspace").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<SharedNote> sharedNotes=new ArrayList<>();
                final int total=(int)dataSnapshot.getChildrenCount();
                final int[] c = {0};
                for(final DataSnapshot child:dataSnapshot.getChildren()){
                    final String id=child.getValue(String.class);
                    ref.child("note/"+id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){
                                if(dataSnapshot.child("title").getValue(String.class)!=null) {
                                    final SharedNote note = new SharedNote();
                                    final int[] i = {0};
                                    note.setLike((int) dataSnapshot.child("like").getChildrenCount());
                                    note.setComment((int) dataSnapshot.child("message").getChildrenCount());
                                    note.setLook((int) dataSnapshot.child("look").getChildrenCount());
                                    note.setLink((int) dataSnapshot.child("link").getChildrenCount());
                                    note.setId(id);
                                    note.setUid(dataSnapshot.child("authorid").getValue(String.class));
                                    ref.child("user/" + dataSnapshot.child("authorid").getValue(String.class) + "/personalspace/" + dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                            note.setTitle(dataSnapshot.child("title").getValue(String.class));
                                            i[0]++;
                                            if (i[0] == 2) {
                                                sharedNotes.add(note);
                                                c[0]++;
                                            }
                                            if (c[0] == total) {
                                                callBack.onSuccess(sharedNotes);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            note.setName(dataSnapshot.child("name").getValue(String.class));
                                            note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                            i[0]++;
                                            if (i[0] == 2) {
                                                sharedNotes.add(note);
                                                c[0]++;
                                            }
                                            if (c[0] == total) {
                                                callBack.onSuccess(sharedNotes);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }else{
                                    ref.child("user/"+uid+"/linkspace/"+child.getKey()).removeValue();
                                }
                            }else{
                                ref.child("user/"+uid+"/linkspace/"+child.getKey()).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callBack.onFailure();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getAllNoteBook(String uid, final OnGetDataCallBack<List<Notebook>> callBack) {
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Notebook> notebooks=new ArrayList<>();
                Log.d("Notebook",dataSnapshot.toString());
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Notebook notebook=new Notebook();
                    notebook.setTitle(child.child("title").getValue(String.class));
                    notebook.setId(child.getKey());
                    notebook.setCover(child.child("cover").getValue(String.class));
                    notebooks.add(notebook);
                }
                callBack.onSuccess(notebooks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getNoteBook(String uid, String bookurl, final OnGetDataCallBack<Notebook> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    Notebook notebook=new Notebook();
                    notebook.setCover(dataSnapshot.child("cover").getValue(String.class));
                    notebook.setId(dataSnapshot.getKey());
                    notebook.setTitle(dataSnapshot.child("title").getValue(String.class));
                    callBack.onSuccess(notebook);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getPersonalNoteFromNotebook(final String uid, final String bookurl, final OnGetDataCallBack<List<PersonalNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl+"/note").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int total= (int) dataSnapshot.getChildrenCount();
                final int[] c = {0};
                final List<PersonalNote> result=new ArrayList<>();
                for(final DataSnapshot child:dataSnapshot.getChildren()){
                    ref.child("user/"+uid+"/personalspace/"+child.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()) {
                                PersonalNote data = new PersonalNote();
                                data.setCoverurl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                data.setColor(dataSnapshot.child("color").getValue(String.class));
                                data.setNoteURL(dataSnapshot.getKey());
                                data.setShare(dataSnapshot.child("isShare").getValue(Boolean.class));
                                data.setUid(dataSnapshot.child("authorid").getValue(String.class));
                                data.setTitle(dataSnapshot.child("title").getValue(String.class));
                                result.add(data);
                            }else{
                                ref.child("user/"+uid+"/notebookspace/"+bookurl+"/note/"+child.getKey()).removeValue();
                            }
                            c[0]++;
                            if(c[0]==total){
                                callBack.onSuccess(result);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPublicCover(final OnGetDataCallBack<List<String>> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("public/cover").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> result=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    result.add(child.getValue(String.class));
                }
                callBack.onSuccess(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void getNewestShareNote(final OnGetDataCallBack<List<SharedNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        final int[] c = {0};
        ref.child("note").orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int total=(int)dataSnapshot.getChildrenCount();
                final List<SharedNote> notes=new ArrayList<>();

                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    final SharedNote note = new SharedNote();
                    note.setLike((int) child.child("like").getChildrenCount());
                    note.setComment((int) child.child("message").getChildrenCount());
                    note.setLook((int) child.child("look").getChildrenCount());
                    note.setLink((int) child.child("link").getChildrenCount());
                    note.setId(child.getKey());
                    note.setUid(child.child("authorid").getValue(String.class));
                    //尋找筆記資料
                    ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                            note.setTitle(dataSnapshot.child("title").getValue(String.class));
                            ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    note.setName(dataSnapshot.child("name").getValue(String.class));
                                    note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                    notes.add(note);
                                    c[0]++;
                                    if (c[0] == total) {
                                        callBack.onSuccess(notes);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getNoteMessage(String noteurl, final OnGetDataCallBack<Message> callBack){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message=new Message();
                message.setId(dataSnapshot.child("uid").getValue(String.class));
                message.setMessage(dataSnapshot.child("message").getValue(String.class));
                message.setimageurl(dataSnapshot.child("photo").getValue(String.class));
                callBack.onSuccess(message);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getHotestShareNote(final OnGetDataCallBack<List<SharedNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        final int[] c = {0};
        ref.child("note").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int total=(int)dataSnapshot.getChildrenCount();
                final List<SharedNote> notes=new ArrayList<>();
                for(DataSnapshot child:dataSnapshot.getChildren()) {
                    final SharedNote note = new SharedNote();
                    note.setLike((int) child.child("like").getChildrenCount());
                    note.setComment((int) child.child("message").getChildrenCount());
                    note.setLook((int) child.child("look").getChildrenCount());
                    note.setLink((int) child.child("link").getChildrenCount());
                    note.setId(child.getKey());
                    note.setUid(child.child("authorid").getValue(String.class));
                    //尋找筆記資料
                    ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                            note.setTitle(dataSnapshot.child("title").getValue(String.class));
                            ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int i=0;
                                    note.setName(dataSnapshot.child("name").getValue(String.class));
                                    note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                    for(SharedNote s:notes){
                                        int look=s.getLook();
                                        if(note.getLook()>look){
                                            break;
                                        }
                                        i++;
                                    }
                                    notes.add(i,note);
                                    c[0]++;
                                    if (c[0] == total) {
                                        callBack.onSuccess(notes);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateLike(final String uid, final String noteurl, final boolean result){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/like").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    Log.d("Child",child.toString());
                    if(uid.equals(child.getValue(String.class))){
                        ref.child("note/"+noteurl+"/like/"+child.getKey()).removeValue();
                        break;
                    }
                }
                if(result){
                    ref.child("note/"+noteurl+"/like/").push().setValue(uid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateLink(final String uid, final String noteurl, final boolean result){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    if(uid.equals(child.getValue(String.class))){
                        ref.child("note/"+noteurl+"/link/"+child.getKey()).removeValue();
                        deleteLinkNote(uid,noteurl);
                        break;
                    }
                }
                if(result){
                    ref.child("note/"+noteurl+"/link/").push().setValue(uid);
                    addLinkNote(uid,noteurl);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateLook(final String uid, final String noteurl){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/look").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    if(uid.equals(child.getValue(String.class))){
                        ref.child("note/"+noteurl+"/look/"+child.getKey()).removeValue();
                        break;
                    }
                }
                ref.child("note/"+noteurl+"/look/").push().setValue(uid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateTitleDetial(String uid, final String url, final TitleDetail titleDetail, final OnUpLoadDataCallback callback) {
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url+"/title").setValue(titleDetail.getTitle());
        ref.child("user/"+uid+"/personalspace/"+url+"/color").setValue(titleDetail.getColor());
        ref.child("user/"+uid+"/personalspace/"+url+"/isShare").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class)==true){
                    ref.child("note/"+url+"/title").setValue(titleDetail.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        callback.onSuccess(null);
    }

    public void addNote(String uid,final OnUpLoadDataCallback<String> callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        String noteid=ref.child("user/"+uid+"/personalspace/").push().getKey();

        Map<String,Object> map=new HashMap<>();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentTime = Calendar.getInstance().getTime();
        map.put("createat",sDateFormat.format(currentTime).toString());
        map.put("updateat",sDateFormat.format(currentTime).toString());
        map.put("authorid",uid);
        map.put("title","Title");
        map.put("color","#FFFFFF");
        map.put("isLinked",false);
        map.put("isShare",false);
        ref.child("user/"+uid+"/personalspace/"+noteid).setValue(map);
        callback.onSuccess(noteid);
    }

    public void addPage(final String uid, final String url, final Page page, final OnUpLoadDataCallback callback) {
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url+"/notecontent/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c=((int)dataSnapshot.getChildrenCount())-1;
                final String pageurl=ref.child("user/"+uid+"/personalspace/"+ url +"/"+c).push().getKey();
                page.setId(pageurl);
                ref.child("user/"+uid+"/personalspace/"+ url +"/"+c).setValue(pageurl).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void addNoteToNoteBook(String uid,String noteurl,String bookurl){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl+"/note").push().setValue(noteurl);
    }

    public void updateNoteContent(final String uid, final String url, Map<String,Object> p, final OnUpLoadDataCallback callback) {
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url+"/notecontent").setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
                Date currentTime = Calendar.getInstance().getTime();

                ref.child("user/"+uid+"/personalspace/"+url+"/updateat").setValue(sDateFormat.format(currentTime).toString());
                callback.onSuccess(null);
            }
        });
    }

    public void updateKeyWord(String uid, final String url, final Map<String,Object> keylist){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/"+url+"/keylist/").setValue(keylist);
        ref.child("user/"+uid+"/personalspace/"+url+"/isShare").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(Boolean.class)==true){
                    ref.child("note/"+url+"/keylist").setValue(keylist);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateNoteBook(final String uid, final String bookurl, final String title, byte[] coverbyte, final OnUpLoadDataCallback<Void> callback){
        StorageReference sef=firebaseStorage.getReference();
        sef.child("user/"+uid+"/notebookspace/"+bookurl+"/cover/cover").putBytes(coverbyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String result=taskSnapshot.getDownloadUrl().toString();
                DatabaseReference ref=firebaseDatabase.getReference();
                ref.child("user/"+uid+"/notebookspace/"+bookurl+"/cover").setValue(result);
                ref.child("user/"+uid+"/notebookspace/"+bookurl+"/title").setValue(title);
                callback.onSuccess(null);
            }
        });
    }

    public void addNoteBook(final String uid, final String title, byte[] coverbyte, final OnUpLoadDataCallback<Void> callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        final String key=ref.child("user/"+uid+"/notebookspace/").push().getKey();

        StorageReference sef=firebaseStorage.getReference();
        sef.child("user/"+uid+"/notebookspace/"+key+"/cover/cover").putBytes(coverbyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String result=taskSnapshot.getDownloadUrl().toString();
                DatabaseReference ref=firebaseDatabase.getReference();
                ref.child("user/"+uid+"/notebookspace/"+key+"/cover").setValue(result);
                ref.child("user/"+uid+"/notebookspace/"+key+"/title").setValue(title);
                callback.onSuccess(null);
            }
        });
    }

    public void addNoteToNoteBook(String uid,String bookurl,List<String> noteurls){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/" + uid + "/notebookspace/" + bookurl + "/note");
        for(String url:noteurls) {
            ref.child("user/" + uid + "/notebookspace/" + bookurl + "/note").push().setValue(url);
        }
    }

    public void addLinkNote(String uid,String noteurl){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/linkspace/").push().setValue(noteurl);
    }

    public void deleteNote(String uid,String url){
        DatabaseReference ref=firebaseDatabase.getReference();
        StorageReference sef=firebaseStorage.getReference();
        //sef.child("user/"+uid+"/personalspace/"+url).delete();
        ref.child("user/"+uid+"/personalspace/"+url).removeValue();
        //ref.child("note/"+url).removeValue();
    }

    public void deleteNoteFromNoteBook(final String uid, final String bookurl, String url){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl+"/note/").orderByValue().equalTo(url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot child:dataSnapshot.getChildren()) {
                        Log.d("DataSnapshot",child.getKey());
                        ref.child("user/" + uid + "/notebookspace/" + bookurl + "/note/" + child.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteLinkNote(final String uid, final String url){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/linkspace/").orderByValue().equalTo(url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    ref.child("user/"+uid+"/linkspace/"+child.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ref.child("note/"+url+"/link").orderByValue().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    ref.child("note/"+url+"/link/"+child.getKey()).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteNoteBook(String uid,String bookurl){
        DatabaseReference ref=firebaseDatabase.getReference();
        StorageReference sef=firebaseStorage.getReference();
        ref.child("user/"+uid+"/notebookspace/"+bookurl).removeValue();
        sef.child("user/"+uid+"/notebookspace/"+bookurl).delete();
    }

    public void unshare(String uid,String noteurl){
        DatabaseReference ref=firebaseDatabase.getReference();
        StorageReference sef=firebaseStorage.getReference();
        ref.child("user/"+uid+"/personalspace/"+noteurl+"/isShare/").setValue(false);
        ref.child("note/"+noteurl).removeValue();
        sef.child("note/"+noteurl).delete();
    }

    public void share(final String uid, final String noteurl, final String college, final String dep, final OnUpLoadDataCallback callback){
        final DatabaseReference ref=firebaseDatabase.getReference();
        checkNoteShareQualify(uid, noteurl, new OnGetDataCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                ref.child("user/"+uid+"/personalspace/").orderByKey().equalTo(noteurl).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,Object> map=new HashMap<>();
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd");
                        Date currentTime = Calendar.getInstance().getTime();
                        map.put("time",sDateFormat.format(currentTime).toString());
                        map.put("college",college);
                        map.put("dep",dep);
                        map.put("authorid",uid);
                        map.put("keylist",dataSnapshot.child(noteurl+"/keylist").getValue());
                        map.put("title",dataSnapshot.child(noteurl+"/title").getValue());
                        ref.child("user/"+uid+"/personalspace/"+noteurl+"/isShare").setValue(true);
                        ref.child("note/"+noteurl).setValue(map);
                        callback.onSuccess(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure();
                    }
                });
            }

            @Override
            public void onFailure() {
                callback.onFailure();
            }
        });

    }

    public void searchSharedNote(String college, final String dep, final String key, final OnGetDataCallBack<List<SharedNote>> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        final List<SharedNote> notes=new ArrayList<>();
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int total=(int)dataSnapshot.getChildrenCount();
                final int[] c={0};
                if(dep.length()!=0){
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        if(child.child("dep").getValue(String.class).equals(dep)) {
                            Log.d("DEP", dep + " " + child.child("dep").getValue(String.class));
                            if (key.length() == 0) {
                                Log.d("DEP", dep + " " + child.child("dep").getValue(String.class));
                                final SharedNote note = new SharedNote();
                                note.setLike((int) child.child("like").getChildrenCount());
                                note.setComment((int) child.child("message").getChildrenCount());
                                note.setLook((int) child.child("look").getChildrenCount());
                                note.setLink((int) child.child("link").getChildrenCount());
                                note.setId(child.getKey());
                                //尋找筆記資料
                                ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                        note.setTitle(dataSnapshot.child("title").getValue(String.class));
                                        ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                note.setName(dataSnapshot.child("name").getValue(String.class));
                                                note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                                notes.add(note);
                                                c[0]++;
                                                if (c[0] == total) {
                                                    callBack.onSuccess(notes);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                String title = child.child("title").getValue(String.class);
                                List<String> keylist = new ArrayList<>();
                                boolean flag = false;
                                for (DataSnapshot key : child.child("keylist").getChildren()) {
                                    keylist.add(key.getValue(String.class));
                                }

                                if (!flag) {
                                    for (String keyword : keylist) {
                                        if (keyword.contains(key)) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                }
                                if (title.contains(key) || keylist.contains(key)) {
                                    flag = true;
                                }
                                if (flag) {
                                    final SharedNote note = new SharedNote();
                                    note.setLike((int) child.child("like").getChildrenCount());
                                    note.setComment((int) child.child("message").getChildrenCount());
                                    note.setLook((int) child.child("look").getChildrenCount());
                                    note.setLink((int) child.child("link").getChildrenCount());
                                    //尋找筆記資料
                                    ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                            note.setTitle(dataSnapshot.child("title").getValue(String.class));
                                            ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    note.setName(dataSnapshot.child("name").getValue(String.class));
                                                    note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                                    notes.add(note);
                                                    c[0]++;
                                                    if (c[0] == total) {
                                                        callBack.onSuccess(notes);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    c[0]++;
                                    if (c[0] == total) {
                                        callBack.onSuccess(notes);
                                    }
                                }
                            }
                        }else{
                            c[0]++;
                            if (c[0] == total) {
                                callBack.onSuccess(notes);
                            }
                        }
                    }
                }else{
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        if(key.length()==0){
                            final SharedNote note = new SharedNote();
                            note.setLike((int) child.child("like").getChildrenCount());
                            note.setComment((int) child.child("message").getChildrenCount());
                            note.setLook((int) child.child("look").getChildrenCount());
                            note.setLink((int) child.child("link").getChildrenCount());
                            //尋找筆記資料
                            ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                    note.setTitle(dataSnapshot.child("title").getValue(String.class));
                                    ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            note.setName(dataSnapshot.child("name").getValue(String.class));
                                            note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                            notes.add(note);
                                            c[0]++;
                                            if (c[0] == total) {
                                                callBack.onSuccess(notes);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else {
                            String title = child.child("title").getValue(String.class);
                            List<String> keylist = new ArrayList<>();
                            boolean flag = false;
                            for (DataSnapshot key : child.child("keylist").getChildren()) {
                                keylist.add(key.getValue(String.class));
                            }

                            if (!flag) {
                                for (String keyword : keylist) {
                                    if (keyword.contains(key)) {
                                        flag = true;
                                        break;
                                    }
                                }
                            }
                            if (title.contains(key) || keylist.contains(key)) {
                                flag = true;
                            }
                            if (flag) {
                                final SharedNote note = new SharedNote();
                                note.setLike((int) child.child("like").getChildrenCount());
                                note.setComment((int) child.child("message").getChildrenCount());
                                note.setLook((int) child.child("look").getChildrenCount());
                                note.setLink((int) child.child("link").getChildrenCount());
                                //尋找筆記資料
                                ref.child("user/" + child.child("authorid").getValue(String.class) + "/personalspace/" + child.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        note.setPhotoUrl(dataSnapshot.child("notecontent/0/imageurl").getValue(String.class));
                                        note.setTitle(dataSnapshot.child("title").getValue(String.class));
                                        ref.child("account/" + dataSnapshot.child("authorid").getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                note.setName(dataSnapshot.child("name").getValue(String.class));
                                                note.setSelfpicture(dataSnapshot.child("photoUrl").getValue(String.class));
                                                notes.add(note);
                                                c[0]++;
                                                if (c[0] == total) {
                                                    callBack.onSuccess(notes);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                c[0]++;
                                if (c[0] == total) {
                                    callBack.onSuccess(notes);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        if(college.length()==0){
            ref.child("note").addListenerForSingleValueEvent(listener);
        }else{
            ref.child("note").orderByChild("college").equalTo(college).addListenerForSingleValueEvent(listener);
        }
    }

    public void addMessage(String noteurl, Message message, final OnUpLoadDataCallback<Void> callback){
        DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("note/"+noteurl+"/message").push().setValue(message.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure();
            }
        });
    }

    public void addImageMessage(final String noteurl, final Message message, byte[] image, final OnUpLoadDataCallback<Void> callback){
        final DatabaseReference ref=firebaseDatabase.getReference();
        StorageReference sef=firebaseStorage.getReference();
        final String key=ref.child("note/"+noteurl+"/message").push().getKey();
        sef.child("note/"+noteurl+"/message/"+key).putBytes(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                message.setimageurl(taskSnapshot.getDownloadUrl().toString());
                ref.child("note/"+noteurl+"/message/"+key).setValue(message.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(null);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure();
            }
        });


    }

    public void checkNoteShareQualify(final String uid, final String url, final OnGetDataCallBack<Boolean> callBack){
        final DatabaseReference ref=firebaseDatabase.getReference();
        ref.child("user/"+uid+"/personalspace/").orderByKey().equalTo(url).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot noteSnapShot=dataSnapshot.child(url);
                boolean isShare=noteSnapShot.child("isShare").getValue(Boolean.class);
                boolean isLink=noteSnapShot.child("isLinked").getValue(Boolean.class);
                callBack.onSuccess(isShare&&isLink);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                callBack.onFailure();
            }
        });
    }
}

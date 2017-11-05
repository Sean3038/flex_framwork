package com.example.ffes.flex_framwork.noteview.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/28.
 */

public class AuthRepository {

    FirebaseAuth auth;
    FirebaseDatabase database;
    public AuthRepository(FirebaseAuth auth, FirebaseDatabase database){
        this.auth=auth;
        this.database=database;
    }

    void login(String account, String password, final SignInOut callBack ){
        auth.signInWithEmailAndPassword(account,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callBack.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onFail();
                    }
                });
    }

    void revoke(){
        auth.signOut();
    }

    public String getCurrentId(){
        FirebaseUser user=auth.getCurrentUser();
        if(user==null){
            return null;
        }
        return user.getUid();
    }

    Uri getPhotoURl(){
        FirebaseUser user=auth.getCurrentUser();
        if(user==null){
            return null;
        }
        return  user.getPhotoUrl();
    }

    public void getUser(final String uid, final OnGetDataCallBack<User> callBack){
        DatabaseReference ref=database.getReference();
        ref.child("account").orderByKey().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    User user = dataSnapshot.child(uid).getValue(User.class);
                    callBack.onSuccess(user);
                }else{
                    callBack.onFailure();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void upDateUser(String uid, User user, final OnUpLoadDataCallback<Void> callback){
        DatabaseReference ref=database.getReference();
        ref.child("account/"+uid).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure();
                    }
                });
    }

    public void createUser(String uid,String email){
        DatabaseReference ref=database.getReference();
        Map<String,Object> map=new HashMap<>();
        map.put("email",email);
        map.put("info","None");
        map.put("name",email);
        map.put("photoUrl","zzz");
        ref.child("account/"+uid).setValue(map);
    }

    public void checkGoogleUser(){
        if(auth.getCurrentUser()!=null) {
            auth.getCurrentUser().getPhotoUrl();
            auth.getCurrentUser().getDisplayName();
            final DatabaseReference ref = database.getReference();
            ref.child("account").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(auth.getUid())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("email", auth.getCurrentUser().getEmail());
                        map.put("info", "None");
                        map.put("name", auth.getCurrentUser().getDisplayName());
                        map.put("photoUrl", auth.getCurrentUser().getPhotoUrl().toString());
                        ref.child("account/" + auth.getUid()).setValue(map);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    interface SignInOut{
        void onSuccess();
        void onFail();
    }
}

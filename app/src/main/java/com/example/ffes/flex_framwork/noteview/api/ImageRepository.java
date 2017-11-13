package com.example.ffes.flex_framwork.noteview.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;

/**
 * Created by Ffes on 2017/10/14.
 */

public class ImageRepository {

    FirebaseStorage storage;

    public ImageRepository(FirebaseStorage storage){
        this.storage=storage;
    }

    public void addSupplyPhoto(String noteurl, String filepath, final OnUpLoadDataCallback<String> callback){
        File file=new File(filepath);
        storage.getReference("images").child(noteurl+"/supply/"+filepath.substring(filepath.lastIndexOf("/")+1))
                .putFile(Uri.fromFile(file))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri=taskSnapshot.getDownloadUrl();
                callback.onSuccess(uri.toString());
            }
        });
    }

    public void addPagePhoto(String noteurl, String filepath, final OnUpLoadDataCallback<String> callback){
        File file=new File(filepath);
        storage.getReference("images").child(noteurl+"/page/"+file.getName())
                .putFile(Uri.fromFile(file))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri uri=taskSnapshot.getDownloadUrl();
                        callback.onSuccess(uri.toString());
                    }
                });
    }

    public void updateUserPicture(String uid, String filepath, final OnUpLoadDataCallback<String> callback){
        File file=new File(filepath);
        storage.getReference("users").child(uid+"/selfpictue")
                .putFile(Uri.fromFile(file))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri uri=taskSnapshot.getDownloadUrl();
                        callback.onSuccess(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure();
                    }
                });
    }

    public void removePagePhoto(Uri imageuri){
//        String path=imageuri.getLastPathSegment();
//       storage.getReference().child(path).delete();
    }

    public void removeSupplyPhoto(Uri imageuri){
//        String path=imageuri.getLastPathSegment();
//        storage.getReference().child(path).delete();
    }

}

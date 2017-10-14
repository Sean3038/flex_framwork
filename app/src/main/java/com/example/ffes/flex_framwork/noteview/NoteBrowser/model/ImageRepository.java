package com.example.ffes.flex_framwork.noteview.NoteBrowser.model;

import android.net.Uri;

import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;

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
        storage.getReference("images").child(noteurl+"/page/"+filepath.substring(filepath.lastIndexOf("/")+1))
                .putFile(Uri.fromFile(file))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri uri=taskSnapshot.getDownloadUrl();
                        callback.onSuccess(uri.toString());
                    }
                });
    }

}

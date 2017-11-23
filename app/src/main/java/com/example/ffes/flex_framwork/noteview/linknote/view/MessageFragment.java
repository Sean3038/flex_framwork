package com.example.ffes.flex_framwork.noteview.linknote.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.linknote.adapter.MessageAdapter;
import com.example.ffes.flex_framwork.noteview.linknote.data.Message;
import com.example.ffes.flex_framwork.noteview.personalspace.view.NoteCover;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;


public class MessageFragment extends Fragment {

    public static final String NOTEURL_KEY="noteurl";

    public static final int REQUEST_GETPHOTO=111;


    ImageButton camera;
    ImageButton send;

    EditText mtext;

    RecyclerView recyclerView;
    MessageAdapter adapter;

    List<Message> mData;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    public static MessageFragment newInstance(String noteurl){
        MessageFragment fragment=new MessageFragment();
        Bundle bundle=new Bundle();
        bundle.putString(NOTEURL_KEY,noteurl);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragement, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
        mtext=(EditText)view.findViewById(R.id.message);
        camera = (ImageButton)view.findViewById(R.id.camera);
        send =(ImageButton)view.findViewById(R.id.send);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            sendPicture();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.list_view);
        mData = new ArrayList<>();
        adapter = new MessageAdapter(getContext(),getChildFragmentManager(), mData);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        prepareMessage();
    }

    private void prepareMessage() {
        noteRepository.getNoteMessage(getArguments().getString(NOTEURL_KEY), new OnGetDataCallBack<Message>() {
            @Override
            public void onSuccess(Message data) {
                adapter.add(data);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void sendMessage(){
        final Message message=new Message();
        message.setMessage(mtext.getText().toString());
        message.setId(authRepository.getCurrentId());
        message.setimageurl("");
        mtext.setText("");
        noteRepository.addMessage(getArguments().getString(NOTEURL_KEY), message, new OnUpLoadDataCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_GETPHOTO:
                if(resultCode==RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    for(Image i:images){
                        File file = new File(i.getPath());
                        int size = (int) file.length();
                        byte[] b = new byte[size];
                        try {
                            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                            buf.read(b, 0, b.length);
                            buf.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message=new Message();
                        message.setId(authRepository.getCurrentId());
                        message.setMessage("");
                        noteRepository.addImageMessage(getArguments().getString(NOTEURL_KEY), message, b, new OnUpLoadDataCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                }
                break;
        }
    }

    private void sendPicture(){
        ImagePicker.create(this)
                .returnAfterFirst(true)
                .showCamera(true)
                .imageTitle("選擇補充照片")
                .single()
                .start(REQUEST_GETPHOTO);
    }

}
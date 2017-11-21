package com.example.ffes.flex_framwork.noteview.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.BaseActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.ImageRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class EditSelfFile extends BaseActivity {

    public static final int REQUEST_GETPHOTO=100;

    EditText iname;
    EditText iaccount;
    EditText iinfo;

    TextView cancel;
    TextView complete;
    TextView change_picture;

    CircularImageView picture;//作者大頭貼
    AuthRepository auth;

    String imageUri;

    DisplayMetrics mPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_self_file);
        mPhone = new DisplayMetrics();
        auth=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);
        initUI();
        init();
        initData();
    }

    private void initUI(){
        change_picture = (TextView) findViewById(R.id.change_picture);
        cancel = (TextView) findViewById(R.id.cancel);
        complete = (TextView) findViewById(R.id.complete);
        iname = (EditText) findViewById(R.id.iname);
        iaccount = (EditText) findViewById(R.id.iaccount);
        iinfo = (EditText) findViewById(R.id.iinfo);
        picture = (CircularImageView) findViewById(R.id.self_picture);
    }

    private void init(){
        change_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.create(EditSelfFile.this)
                        .returnAfterFirst(true)
                        .showCamera(true)
                        .imageTitle("選擇補充照片")
                        .single()
                        .start(REQUEST_GETPHOTO);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();//取消退出
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress("儲存中....");
                ImageRepository imageRepository=new ImageRepository(FirebaseStorage.getInstance());
                final User user = new User();
                user.setAccount(iaccount.getText().toString());
                user.setName(iname.getText().toString());
                user.setInfo(iinfo.getText().toString());

                imageRepository.updateUserPicture(auth.getCurrentId(), imageUri, new OnUpLoadDataCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        user.setPhotoUrl(s);
                        auth.upDateUser(auth.getCurrentId(), user, new OnUpLoadDataCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                setResult(RESULT_OK);
                                dismissProgress();
                                finish();//完成儲存資料，寫進資料庫，並退出
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        user.setPhotoUrl(imageUri);
                        auth.upDateUser(auth.getCurrentId(), user, new OnUpLoadDataCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                setResult(RESULT_OK);
                                dismissProgress();
                                finish();//完成儲存資料，寫進資料庫，並退出
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                });
            }
        });
    }

    private void initData() {
        auth.getUser(auth.getCurrentId(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                Picasso.with(EditSelfFile.this).load(data.getPhotoUrl()).resize(400,400).into(picture);
                imageUri=data.getPhotoUrl();
                iinfo.setText(data.getInfo());
                iname.setText(data.getName());
                iaccount.setText(data.getAccount());
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public static void startAction(Fragment fragment, int RequestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getContext(), EditSelfFile.class);
        fragment.startActivityForResult(intent,RequestCode);
    }

    public static  void startAction(Activity activity,int RequestCode){
        Intent intent = new Intent();
        intent.setClass(activity, EditSelfFile.class);
        activity.startActivityForResult(intent,RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_GETPHOTO && resultCode==RESULT_OK){
            ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
            for(Image i:images){
                imageUri=i.getPath();
                Bitmap bitmap= BitmapFactory.decodeFile(i.getPath());
                picture.setImageBitmap(bitmap);

            }
        }
    }

}

package com.example.ffes.flex_framwork.noteview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.SpaceActivity;
import com.example.ffes.flex_framwork.noteview.Login.view.LoginActivity;
import com.example.ffes.flex_framwork.noteview.MyAccountMVP.view.AccountEditActivity;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteBrowserActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int REQUEST_LOGIN=100;

    Button editer;
    Button browser;
    Button addnote;
    Button overview;

    FirebaseAuth auth;

    @Override
    protected void onDestroy() {
        auth.signOut();
        super.onDestroy();
    }

    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onStop() {
        if(listener!=null){
            auth.removeAuthStateListener(listener);
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editer= (Button) findViewById(R.id.button3);
        browser= (Button) findViewById(R.id.button2);
        addnote=(Button)findViewById(R.id.button4);
        overview=(Button)findViewById(R.id.button5);
        editer.setOnClickListener(this);
        browser.setOnClickListener(this);
        addnote.setOnClickListener(this);
        overview.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null){
                    startActivity(new Intent(TestActivity.this,LoginActivity.class));
                }
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode != RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button2:
                //NoteBrowserActivity.start(this,"sdf4K5df6a");
                //overridePendingTransition(R.anim.right_1,R.anim.right_2);
                Intent intent=new Intent();
                intent.setClass(this,AccountEditActivity.class);
                startActivity(intent);
                break;
            case R.id.button3:
                //NoteEditorActivity.start(this,"sdf4K5df6a");
                //overridePendingTransition(R.anim.right_1,R.anim.right_2);
                break;
            case R.id.button4:
                NoteEditorActivity.start(this,null);
                break;
            case R.id.button5:
                SpaceActivity.start(this);
                break;
        }
    }
}

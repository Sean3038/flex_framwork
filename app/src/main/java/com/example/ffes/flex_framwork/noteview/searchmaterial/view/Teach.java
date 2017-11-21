package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;


/**
 * Created by user on 2017/11/15.
 */

public class Teach extends AppCompatActivity {

    TextView title;

    ImageButton close;

    ImageView teach1;
    ImageView teach2;
    ImageView teach3;

    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teach);
        initUI();
        init();
    }

    private void initUI(){
        title = (TextView)findViewById(R.id.title_content);
        close = (ImageButton)findViewById(R.id.close);
        teach1 = (ImageView)findViewById(R.id.teach1);
        teach2 = (ImageView)findViewById(R.id.teach2);
        teach3 = (ImageView)findViewById(R.id.teach3);
        sv = (ScrollView)findViewById(R.id.sv);
    }

    private void init(){
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, Teach.class);
        context.startActivity(intent);
    }
}


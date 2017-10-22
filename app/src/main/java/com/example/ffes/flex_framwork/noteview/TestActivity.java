package com.example.ffes.flex_framwork.noteview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteBrowserActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    Button editer;
    Button browser;
    Button addnote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editer= (Button) findViewById(R.id.button3);
        browser= (Button) findViewById(R.id.button2);
        addnote=(Button)findViewById(R.id.button4);
        editer.setOnClickListener(this);
        browser.setOnClickListener(this);
        addnote.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button2:
                NoteBrowserActivity.start(this,"sdf4K5df6a");
                break;
            case R.id.button3:
                NoteEditorActivity.start(this,"sdf4K5df6a");
                break;
            case R.id.button4:
                NoteEditorActivity.start(this,null);
        }
    }
}

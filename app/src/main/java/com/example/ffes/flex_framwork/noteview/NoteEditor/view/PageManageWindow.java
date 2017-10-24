package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.PageListAdapter;

/**
 * Created by Ffes on 2017/10/23.
 */

public class PageManageWindow implements PageListAdapter.OnAddPageListener{

    ViewGroup root;

    RecyclerView pagelist;
    LinearLayout pagemanage;

    ImageView closepagebtn;
    Button add_page;
    Button link_page;

    PageManageWindow(ViewGroup root, PageListAdapter adpater, View.OnClickListener addpageclick, View.OnClickListener linkpageclick){
        this.root=root;
        pagelist=(RecyclerView)root.findViewById(R.id.pagelistview);
        pagemanage=(LinearLayout)root.findViewById(R.id.pagemanage);
        closepagebtn=(ImageView)root.findViewById(R.id.closemanage);
        add_page=(Button)root.findViewById(R.id.add_page);
        link_page=(Button)root.findViewById(R.id.linkpage);
        adpater.setListener(this);
        pagelist.setAdapter(adpater);
        pagelist.setLayoutManager(new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false));
        closepagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagemanage.setVisibility(View.GONE);
                pagelist.setVisibility(View.VISIBLE);
            }
        });
        add_page.setOnClickListener(addpageclick);
        link_page.setOnClickListener(linkpageclick);
    }

    public void openWindow(){
        root.setVisibility(View.VISIBLE);
    }

    public void closeWindow(){
        root.setVisibility(View.GONE);
    }

    @Override
    public void onAddPage() {
        pagelist.setVisibility(View.GONE);
        pagemanage.setVisibility(View.VISIBLE);
    }
}

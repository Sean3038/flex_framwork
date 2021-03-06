package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.example.ffes.flex_framwork.R;

import java.util.Arrays;

/**
 * Created by Ffes on 2017/10/30.
 */

public class EngineDepView  extends  DepView{

    DepAdapter depAdapter;

    public EngineDepView(@NonNull Context context) {
        super(context);
        RecyclerView recyclerView=new RecyclerView(context);
        depAdapter=new DepAdapter(context, Arrays.asList(context.getResources().getStringArray(R.array.Engine)));
        recyclerView.setAdapter(depAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false));
        this.addView(recyclerView);
    }

    public String getDep(){
        return depAdapter.getDep();
    }
}

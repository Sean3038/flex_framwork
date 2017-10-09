package com.example.ffes.flex_framwork.noteview.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyPageAdapter;

import java.util.List;

/**
 * Created by Ffes on 2017/9/1.
 */

public class SupplyView extends FrameLayout implements View.OnClickListener{

    ViewPager supplycontent;
    SupplyPageAdapter supplyPageAdapter;
    ImageView next;
    ImageView back;
    TextView notify;

    public SupplyView(@NonNull Context context) {
        super(context);
        init();
    }

    public SupplyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SupplyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(),R.layout.supplylayout,this);
        supplycontent=(ViewPager) findViewById(R.id.content);
        next=(ImageView)findViewById(R.id.next);
        back=(ImageView)findViewById(R.id.back);
        notify=(TextView)findViewById(R.id.nosupply);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void setSupplyPageAdapter(SupplyPageAdapter adapter){
        supplyPageAdapter =adapter;
        supplycontent.setAdapter(supplyPageAdapter);
        if(supplyPageAdapter.getCount()==0){
            findViewById(R.id.nosupply).setVisibility(VISIBLE);
        }else{
            findViewById(R.id.nosupply).setVisibility(GONE);
        }
        supplycontent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    back.setVisibility(INVISIBLE);
                }else{
                    back.setVisibility(VISIBLE);
                }
                if(position== supplyPageAdapter.getCount()-1) {
                    next.setVisibility(INVISIBLE);
                }else{
                    next.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void changeData(String noteURL, List<Integer> supplies){
        supplyPageAdapter.setSupplyData(noteURL,supplies);
        if(supplies.isEmpty()){
            notify.setVisibility(VISIBLE);
        }else{
            notify.setVisibility(GONE);
        }
        if(supplycontent.getCurrentItem()==0){back.setVisibility(INVISIBLE);}else{back.setVisibility(VISIBLE);}
        if(supplycontent.getCurrentItem()>= supplyPageAdapter.getCount()-1){next.setVisibility(INVISIBLE);}else{next.setVisibility(VISIBLE);}
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                supplycontent.setCurrentItem(supplycontent.getCurrentItem()-1,true);
                break;
            case R.id.next:
                supplycontent.setCurrentItem(supplycontent.getCurrentItem()+1,true);
        }
    }
}

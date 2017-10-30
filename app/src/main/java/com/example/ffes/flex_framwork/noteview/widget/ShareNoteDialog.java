package com.example.ffes.flex_framwork.noteview.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.CollegeAdapter;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.DepView;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.DesignDepView;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.EngineDepView;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.HumanDepView;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.ManagementDepView;
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.PersonalSpaceFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ffes on 2017/10/29.
 */

public class ShareNoteDialog extends DialogFragment {

    public static final String URL_KEY="NOTEURL";


    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.confirm)
    Button confirm;
    @BindView(R.id.title_tab)
    TabLayout titleTab;
    Unbinder unbinder;

    List<DepView> views;
    OnClickShare listener;
    public interface OnClickShare{
        void onShare(String noteurl,String college,String dep);
    }

    public static ShareNoteDialog newInstance(String noteurl,OnClickShare listener) {
        ShareNoteDialog dialog = new ShareNoteDialog();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteurl);
        dialog.setArguments(bundle);
        dialog.setListener(listener);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.share_note_dialog, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDepView();
        initTab();
    }

    private void initDepView() {
        views=new ArrayList<>();
        views.add(new ManagementDepView(getContext()));
        views.add(new EngineDepView(getContext()));
        views.add(new DesignDepView(getContext()));
        views.add(new HumanDepView(getContext()));
    }

    private void initTab(){
        titleTab.addTab(titleTab.newTab().setText("管理"));
        titleTab.addTab(titleTab.newTab().setText("工程"));
        titleTab.addTab(titleTab.newTab().setText("設計"));
        titleTab.addTab(titleTab.newTab().setText("人文"));

        CollegeAdapter collegeAdapter=new CollegeAdapter(views);
        viewpager.setAdapter(collegeAdapter);
        initlistenr();
    }

    private void initlistenr(){
        titleTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(titleTab));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onShare(getArguments().getString(URL_KEY),titleTab.getTabAt(titleTab.getSelectedTabPosition()).getText().toString(),views.get(viewpager.getCurrentItem()).getDep());
                dismiss();
            }
        });
    }

    public void setListener(OnClickShare listener) {
        this.listener = listener;
    }
}

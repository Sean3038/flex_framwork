package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;


import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.personalspace.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NoteSpaceFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String[] titles = new String[]{"我的筆記", "引用筆記","筆記本"};
    List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_space_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.fl);
        init();
    }

    private void init(){
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String tab:titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.addOnTabSelectedListener(this);
        fragments=new ArrayList<>();
        fragments.add(new SelfNoteFragment());
        fragments.add(new LinkNoteFragment());
        fragments.add(new NoteBookFragment());
        viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),titles,fragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public static NoteSpaceFragment newInstance(){
        NoteSpaceFragment fragment=new NoteSpaceFragment();
        return fragment;
    }
}

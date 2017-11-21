package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.mauker.materialsearchview.MaterialSearchView;

public class SearchNoteFragement extends Fragment implements View.OnClickListener{
    MaterialSearchView searchView;
    Toolbar toolbar;
    Activity mActivity;

    RelativeLayout tab1;
    RelativeLayout tab2;
    RelativeLayout menu;

    int currentIndex = 0;
    ArrayList<Fragment> fragmentArrayList;
    Fragment mCurrentFrgment;

    MenuSearchFragement menuSearchFragement;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle drawerToggle;

    HashMap<String,List<String>> map = new HashMap<String,List<String>>();
    ArrayList<String> mList;

    FragmentManager fm;
    FragmentTransaction tx;

    public static SearchNoteFragement newInstance(){
        SearchNoteFragement fragement=new SearchNoteFragement();
        return fragement;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_note_fragement, container, false);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        menu = (RelativeLayout) view.findViewById(R.id.menu);

        menuSearchFragement = MenuSearchFragement.newInstance();

        fm = getChildFragmentManager();
        tx = fm.beginTransaction();
        tx.replace(R.id.menu, menuSearchFragement);
        tx.commit();

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        drawerToggle.syncState();


        tab1 = (RelativeLayout) view.findViewById(R.id.tab1);
        tab1.setOnClickListener(this);
        tab2 = (RelativeLayout) view.findViewById(R.id.tab2);
        tab2.setOnClickListener(this);
        searchView = (MaterialSearchView) view.findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("QUERY",query);
                SearchResult.startAction(getContext(),"","",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewOpened() {
                // Do something once the view is open.
            }

            @Override
            public void onSearchViewClosed() {
                // Do something once the view is closed.
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Do something when the suggestion list is clicked.
                String suggestion = searchView.getSuggestionAtPosition(position);

                searchView.setQuery(suggestion, false);
            }
        });

        searchView.adjustTintAlpha(0.8f);


        initComponent();
        initFragment();

        return view;
    }

    private void initComponent() {
        tab1.setTag(0);
        tab2.setTag(1);
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<>(3);
        fragmentArrayList.add(NewNoteFragment.newInstance());
        fragmentArrayList.add(HotNoteFragment.newInstance());

        tab1.setSelected(true);
        changeTab(0);
    }

    @Override
    public void onClick(View view) {
        changeTab((Integer) view.getTag());
    }

    private void changeTab(int i) {
        currentIndex = i;
        TextView tx1 = (TextView) tab1.findViewById(R.id.tx1);
        TextView tx2 = (TextView) tab2.findViewById(R.id.tx2);

        tx1.setTextColor(Color.parseColor("#8BA7CB"));
        tx1.setTextSize(18);
        tx2.setTextColor(Color.parseColor("#8BA7CB"));
        tx2.setTextSize(18);

        switch (i) {
            case 0:
                tx1.setTextColor(Color.parseColor("#013270"));
                tx1.setTextSize(22);
                break;
            case 1:
                tx2.setTextColor(Color.parseColor("#013270"));
                tx2.setTextSize(22);
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(i);
        }
        mCurrentFrgment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment1, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();

        Log.d("Fragment",""+currentIndex+" "+fragmentArrayList.size()+" "+i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);


        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (id) {
            case R.id.search:
                // Open the search view on the menu item click.

                searchView.openSearch();
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}


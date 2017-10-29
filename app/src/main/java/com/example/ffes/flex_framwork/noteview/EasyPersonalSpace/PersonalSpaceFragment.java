package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ffes.flex_framwork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ffes on 2017/10/29.
 */

public class PersonalSpaceFragment extends Fragment {

    @BindView(R.id.notelist)
    RecyclerView notelist;
    Unbinder unbinder;

    PersonalNoteAdapter adapter;

    public static PersonalSpaceFragment newInstance() {
        PersonalSpaceFragment fragment = new PersonalSpaceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personalspace, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new PersonalNoteAdapter(this.getActivity());
        notelist.setLayoutManager(new GridLayoutManager(this.getActivity(),3,GridLayoutManager.VERTICAL,false));
        notelist.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

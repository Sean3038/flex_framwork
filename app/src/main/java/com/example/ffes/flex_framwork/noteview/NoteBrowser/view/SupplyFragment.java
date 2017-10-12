package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyAdapter;

/**
 * Created by Ffes on 2017/8/29.
 */

public class SupplyFragment extends Fragment{
    public static final String Editor_Key="Editor";

    ProgressBar progressBar;
    RecyclerView recyclerView;
    SupplyAdapter listAdapter;

    public static SupplyFragment newInstance(String noteUrl,int page,boolean isEditor){
        SupplyFragment supplyFragment=new SupplyFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean(Editor_Key,isEditor);
        supplyFragment.setArguments(bundle);
        return supplyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.supplyfragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)view.findViewById(R.id.supplylist);

        listAdapter=new SupplyAdapter(getContext(),getArguments().getBoolean(Editor_Key));
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(manager);
        listAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.scrollToPosition(listAdapter.getItemCount()-1);
            }

            @Override
            public void onChanged() {
                super.onChanged();
                recyclerView.scrollToPosition(listAdapter.getItemCount());
            }
        });
    }
}

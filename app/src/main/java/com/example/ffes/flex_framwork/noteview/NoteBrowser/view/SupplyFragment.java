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
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.SupplyStateModel;

/**
 * Created by Ffes on 2017/8/29.
 */

public class SupplyFragment extends Fragment{
    public static final String Editor_Key="Editor";

    ProgressBar progressBar;
    RecyclerView recyclerView;
    SupplyAdapter listAdapter;

    SupplyStateModel stateModel;

    public static SupplyFragment newInstance(SupplyStateModel statemodel, boolean isEditor){
        SupplyFragment supplyFragment=new SupplyFragment();
        Bundle bundle=new Bundle();
        bundle.putBoolean(Editor_Key,isEditor);
        supplyFragment.setArguments(bundle);
        supplyFragment.setSupplyStateModel(statemodel);
        return supplyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.supplyfragment,container,false);
    }

    @Override
    public void onDestroy() {
        stateModel.removeModel(listAdapter);
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        recyclerView=(RecyclerView)view.findViewById(R.id.supplylist);

        listAdapter=new SupplyAdapter(getContext(),getFragmentManager(),getArguments().getBoolean(Editor_Key));
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(manager);
        stateModel.addModel(listAdapter);
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

    public void setSupplyStateModel(SupplyStateModel model){
        stateModel=model;
    }
}

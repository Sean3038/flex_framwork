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
import com.example.ffes.flex_framwork.noteview.NoteBrowser.SupplyShowContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter.SupplyAdapter;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter.SupplyShowPresenter;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

/**
 * Created by Ffes on 2017/8/29.
 */

public class SupplyFragment extends Fragment implements SupplyShowContract.View{
    public static final String URL_KEY="NoteURL";
    public static final String PAGE_KEY="Page";
    public static final String Editor_Key="Editor";

    ProgressBar progressBar;
    RecyclerView recyclerView;
    SupplyAdapter listAdapter;

    SupplyShowContract.Presenter presenter;

    public static SupplyFragment newInstance(String noteUrl,int page,boolean isEditor){
        SupplyFragment supplyFragment=new SupplyFragment();
        Bundle bundle=new Bundle();
        bundle.putString(URL_KEY,noteUrl);
        bundle.putInt(PAGE_KEY,page);
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
    public void onResume() {
        super.onResume();
        presenter.loadSupply(getArguments().getString(URL_KEY),getArguments().getInt(PAGE_KEY));
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
        presenter=new SupplyShowPresenter(this, new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance()));
    }

    @Override
    public void setSupplyList(List<Supply> supply) {
        listAdapter.loadList(supply);
    }

    public void addSupply(Supply supply){
        listAdapter.add(supply);
    }
}

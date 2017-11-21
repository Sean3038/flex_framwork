package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.account.EditSelfFile;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.widget.CircleTransformation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuSearchFragement extends Fragment implements View.OnClickListener{

    public static final int REQUEST_SEARCH=101;


    TextView edit;
    TextView accountName;
    TextView teach;

    TextView t1;

    RelativeLayout r1;
    RelativeLayout child;

    ImageButton back;

    CircularImageView iv;

    DepAdapter depAdapter;
    DepAdapter adapter;

    RecyclerView recyclerView;
    RecyclerView depview;

    AuthRepository authRepository;

    String college="";

    public static MenuSearchFragement newInstance(){
        MenuSearchFragement fragment = new MenuSearchFragement();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_search_fragement, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        iv = (CircularImageView) view.findViewById(R.id.self_picture);
        accountName = (TextView) view.findViewById(R.id.accountName);
        edit = (TextView) view.findViewById(R.id.edit);
        recyclerView=(RecyclerView) view.findViewById(R.id.list_view);
        back=(ImageButton)view.findViewById(R.id.ib3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidedep();
            }
        });
        t1=(TextView)view.findViewById(R.id.t1);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditSelfFile.startAction(getActivity(),REQUEST_SEARCH);
            }
        });

        teach = (TextView) view.findViewById(R.id.teach);
        teach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Teach.startAction(getActivity());
            }
        });
        r1=(RelativeLayout)view.findViewById(R.id.r1);
        child=(RelativeLayout)view.findViewById(R.id.child);
        depview=(RecyclerView)view.findViewById(R.id.depview);
        initDeplist();
        authRepository.getUser(authRepository.getCurrentId(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                accountName.setText(data.getName());
                Picasso.with(getContext()).load(data.getPhotoUrl())
                        .noFade()
                        .fit()
                        .centerCrop()
                        .transform(new CircleTransformation())
                        .into(iv);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void initDeplist(){
        depAdapter = new DepAdapter(getContext(), new DepAdapter.OnItemClick() {
            @Override
            public void ItemSelect(String item) {
                adapter=new DepAdapter(getContext(), new DepAdapter.OnItemClick() {
                    @Override
                    public void ItemSelect(String item) {
                        SearchResult.startAction(getContext(),college,item,"");
                    }
                });
                depview.setLayoutManager(new LinearLayoutManager(getContext()));
                depview.setItemAnimator(new DefaultItemAnimator());
                depview.setAdapter(adapter);
                List<String> list=new ArrayList<>();
                switch(item){
                    case "管理學院":
                        list.clear();
                        list.addAll(Arrays.asList(getResources().getStringArray(R.array.Management)));
                        t1.setText("管理學院");
                        college="管理";
                        break;
                    case "工程學院":
                        list.clear();
                        list.addAll(Arrays.asList(getResources().getStringArray(R.array.Engine)));
                        t1.setText("工程學院");
                        college="工程";
                        break;
                    case "設計學院":
                        list.clear();
                        list.addAll(Arrays.asList(getResources().getStringArray(R.array.Design)));
                        t1.setText("設計學院");
                        college="設計";
                        break;
                    case "人文學院":
                        list.clear();
                        list.addAll(Arrays.asList(getResources().getStringArray(R.array.Human)));
                        t1.setText("人文學院");
                        college="人文";
                        break;
                }
                adapter.setData(list);
                showdep();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(depAdapter);
        recyclerView.setHasFixedSize(true);
        depAdapter.setData(Arrays.asList(getResources().getStringArray(R.array.College)));
    }
    public void setData(List<String> data){
        depAdapter.setData(data);
    }

    public void showdep(){
        child.setVisibility(View.VISIBLE);
        r1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    public void hidedep(){
        child.setVisibility(View.GONE);
        r1.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {

    }
}

package com.example.ffes.flex_framwork.noteview.linknote.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteBrowserActivity;
import com.squareup.picasso.Picasso;


public class NoteFragment extends Fragment {

    public static final String NOTEURL_KEY="noteurl";
    public static final String PHOTOURL_KEY="photourl";
    public static final String UID_KEY="uid";
    ImageView note;

    public static NoteFragment newInstance(String noteurl,String uid,String photourl){
        NoteFragment fragment=new NoteFragment();
        Bundle bundle=new Bundle();
        bundle.putString(NOTEURL_KEY,noteurl);
        bundle.putString(PHOTOURL_KEY,photourl);
        bundle.putString(UID_KEY,uid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        note = (ImageView)view.findViewById(R.id.note);
        Picasso.with(getContext()).load(getArguments().getString(PHOTOURL_KEY)).centerInside().resize(800,800).noPlaceholder().into(note);
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切換到瀏覽筆記頁面
                NoteBrowserActivity.start(getContext(),getArguments().getString(UID_KEY),getArguments().getString(NOTEURL_KEY));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

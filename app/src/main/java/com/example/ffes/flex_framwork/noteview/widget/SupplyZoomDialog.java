package com.example.ffes.flex_framwork.noteview.widget;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.ffes.flex_framwork.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;

/**
 * Created by Ffes on 2017/10/22.
 */

public class SupplyZoomDialog extends DialogFragment {
    public static final String IMAGE="image";
    PhotoView photoView;

    public static SupplyZoomDialog newInstance(String imageurl){
        SupplyZoomDialog fragment=new SupplyZoomDialog();
        Bundle bundle=new Bundle();
        bundle.putString(IMAGE,imageurl);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.supplyzoom,container);
        return v;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        photoView=(PhotoView)view.findViewById(R.id.photo);
        Picasso.with(getContext())
                .load(getArguments().getString(IMAGE))
                .resize(1500,1500)
                .centerInside()
                .into(photoView);
    }
}

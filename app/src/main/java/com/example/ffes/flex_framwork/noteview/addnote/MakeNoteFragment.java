package com.example.ffes.flex_framwork.noteview.addnote;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.squareup.picasso.Picasso;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MakeNoteFragment extends Fragment implements View.OnClickListener{

    String[] permissions = new String[]{CAMERA, WRITE_EXTERNAL_STORAGE};
    public static final int GET_CAMERA = 0;
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private final static int PHOTO = 99;

    ImageView iv;

    public static MakeNoteFragment newInstance() {

        Bundle args = new Bundle();
        MakeNoteFragment fragment = new MakeNoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GET_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    getActivity().finish();
                }
                return;
            }
            case REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    getActivity().finish();
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_note_fragment, container, false);
        iv = (ImageView) view.findViewById(R.id.self_picture);
        Picasso.with(getContext()).load(R.drawable.camera).fit().centerInside().into(iv);
        iv.setOnClickListener(this);
        return view;
    }




    @Override
    public void onClick(View view) {
        NoteEditorActivity.start(getContext(),null);
    }
}

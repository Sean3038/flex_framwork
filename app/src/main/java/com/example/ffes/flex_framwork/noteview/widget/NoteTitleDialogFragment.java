package com.example.ffes.flex_framwork.noteview.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/9/17.
 */

public class NoteTitleDialogFragment extends DialogFragment implements View.OnClickListener{

    public static final String TITLE_KEY="title";
    public static final String COLOR_KEY="color";

    ColorPickerGroup pickerGroup;
    TextView title;
    Button confirm;
    Button cancel;
    Callback callback;

    public static NoteTitleDialogFragment newInstance(String title,int color,Callback callback){
        NoteTitleDialogFragment fragment=new NoteTitleDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString(TITLE_KEY,title);
        bundle.putInt(COLOR_KEY,color);
        fragment.setArguments(bundle);
        fragment.setCallback(callback);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.title_editor_layout,container);
        return v;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        pickerGroup=(ColorPickerGroup)view.findViewById(R.id.colorgroup);
        title=(TextView)view.findViewById(R.id.editText);
        confirm=(Button)view.findViewById(R.id.confirm);
        cancel=(Button)view.findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        pickerGroup.init();
        init();
    }

    private void init(){
        Bundle bundle=getArguments();
        setTitle(bundle.getString(TITLE_KEY));
        setCurrentColor(bundle.getInt(COLOR_KEY));
    }

    public void setCurrentColor(int color){
        pickerGroup.setCurrentColor(color);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    @Override
    public void onResume() {
        super.onResume();
        setCurrentColor(getArguments().getInt(COLOR_KEY));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                if(callback!=null){
                    callback.onConfirm(title.getText().toString(),pickerGroup.getCurrentColor());
                }
                dismiss();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public interface Callback{
        void onConfirm(String title,int color);
    }
}

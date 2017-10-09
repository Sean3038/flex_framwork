package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/9/26.
 */

public class AddKeyDialogFragment extends DialogFragment{
    EditText keyword;
    Button confirm;
    OnConfirmListener listener;

    interface OnConfirmListener{
        void onConfirm(String key);
    }


    public static AddKeyDialogFragment newInstance(OnConfirmListener listener){
        AddKeyDialogFragment addKeyDialogFragment=new AddKeyDialogFragment();
        addKeyDialogFragment.setListener(listener);
        return addKeyDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View view= LayoutInflater.from(getContext()).inflate(R.layout.add_key_dialog,null,false);
        builder.setView(view);
        keyword=(EditText) view.findViewById(R.id.keywordtext);
        confirm=(Button) view.findViewById(R.id.addkeyconfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onConfirm(keyword.getText().toString());

                }
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(keyword.getWindowToken(), 0);
                dismiss();
            }
        });
        return builder.create();
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }
}

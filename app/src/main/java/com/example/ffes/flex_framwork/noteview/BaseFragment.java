package com.example.ffes.flex_framwork.noteview;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/10/30.
 */

public abstract class BaseFragment extends Fragment{
    ProgressDialog progressDialog;
    public void showProgress(String message){
        if (progressDialog != null && progressDialog.isShowing())
            dismissProgress();

        progressDialog = ProgressDialog.show(getContext(),getResources().getString(R.string.app_name),message);
    }

    protected void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

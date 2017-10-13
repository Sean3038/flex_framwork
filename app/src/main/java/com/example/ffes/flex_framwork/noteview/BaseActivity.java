package com.example.ffes.flex_framwork.noteview;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.ffes.flex_framwork.R;

/**
 * Created by Ffes on 2017/10/13.
 */

public abstract class BaseActivity extends AppCompatActivity{
    ProgressDialog progressDialog;
    public void showProgress(String message){
        if (progressDialog != null && progressDialog.isShowing())
            dismissProgress();

        progressDialog = ProgressDialog.show(this,getResources().getString(R.string.app_name),message);
    }

    protected void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

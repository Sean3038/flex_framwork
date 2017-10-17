package com.example.ffes.flex_framwork.noteview.NoteBrowser.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.widget.LottieButton;

/**
 * Created by Ffes on 2017/10/16.
 */

public class FilterToolBar {

    LottieButton keylisttoggle;
    ImageView supplytoggle;
    TextView titleview;

    PopupWindow keylistwindow;

    FilterToolBar(View view, LottieButton.Callback callback, View.OnClickListener titleclick, View.OnClickListener supplyclick){
        keylisttoggle=(LottieButton)view.findViewById(R.id.keylisttoggle);
        titleview=(TextView)view.findViewById(R.id.title);
        supplytoggle=(ImageView)view.findViewById(R.id.supplytoggle);
        keylisttoggle.setOnCallbackListener(callback);
        titleview.setOnClickListener(titleclick);
        supplytoggle.setOnClickListener(supplyclick);
    }

    void setPopupWindow(PopupWindow window){
        keylistwindow=window;
    };

    public void showKeyList() {
        keylistwindow.showAsDropDown(keylisttoggle,0,84);
    }


    public void hideKeyList() {
        keylistwindow.dismiss();
    }

    public void setTitle(String title) {
        titleview.setText(title);
    }
}

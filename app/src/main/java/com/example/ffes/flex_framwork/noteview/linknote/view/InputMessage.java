package com.example.ffes.flex_framwork.noteview.linknote.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.ffes.flex_framwork.R;


public class InputMessage extends LinearLayout implements TextWatcher, View.OnClickListener {

    EditText et_message;//輸入訊息
    ImageButton input;

    TypeMessage typeMessage;


    public InputMessage(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.input_message, this, true);
        /***找出控件*/
        et_message = (EditText) findViewById(R.id.et_message);
        input = (ImageButton) findViewById(R.id.input);
        input.setOnClickListener(this);
        et_message.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String input = et_message.getText().toString().trim();

    }

    @Override
    public void onClick(View view) {
        et_message.setText("");
    }

    public void setTypeMessage(TypeMessage typeMessage){
        this.typeMessage=typeMessage;
    }



    public interface TypeMessage{
        void message(String character);
    }
}

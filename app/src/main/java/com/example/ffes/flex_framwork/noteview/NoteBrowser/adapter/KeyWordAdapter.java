package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyWordStateModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.KeyWordDataModel;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/9/13.
 */

public class KeyWordAdapter extends RecyclerView.Adapter<KeyWordAdapter.ViewHolder>implements KeyWordDataModel{

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView delete;
        TextView content;
        OnKeyClick keyClick;
        OnDeleteKeyClick deleteKeyClick;

        public ViewHolder(View itemView) {
            super(itemView);
            content=(TextView)itemView.findViewById(R.id.keyword);
            delete=(ImageView)itemView.findViewById(R.id.delete);
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(keyClick!=null) {
                        keyClick.onKeyClick(content.getText().toString());
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(deleteKeyClick!=null) {
                        deleteKeyClick.onDeleteKeyClick(content.getText().toString());
                    }
                }
            });
        }

        public void setKeyWord(String key,int color){
            content.setText(key);
            content.setTextColor(color);
        }

        public void showDeleteButton(){
            delete.setVisibility(View.VISIBLE);
        }

        public void hideDeleteButton(){
            delete.setVisibility(View.GONE);
        }

        public void setKeyClick(OnKeyClick keyClick) {
            this.keyClick = keyClick;
        }

        public void setDeleteKeyClick(OnDeleteKeyClick deleteKeyClick) {
            this.deleteKeyClick = deleteKeyClick;
        }
    }

    OnKeyClick keyClick;
    OnDeleteKeyClick deleteKeyClick;

    KeyWordStateModel keyWordStateModel;

    boolean isEdit;

    public KeyWordAdapter(){
        isEdit=false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.keylistitem,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(isEdit){
            holder.showDeleteButton();
        }else{
            holder.hideDeleteButton();
        }
        holder.setKeyWord(keyWordStateModel.getKeyWord(position).getKeyword(),keyWordStateModel.getKeyWord(position).getColor());
        holder.setKeyClick(keyClick);
        holder.setDeleteKeyClick(deleteKeyClick);
    }

    @Override
    public int getItemCount() {
        return keyWordStateModel.getKeyCount();
    }


    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    public void setKeyClick(OnKeyClick keyClick) {
        this.keyClick = keyClick;
        notifyDataSetChanged();
    }

    public void setDeleteKeyClick(OnDeleteKeyClick deleteKeyClick) {
        this.deleteKeyClick = deleteKeyClick;
        notifyDataSetChanged();
    }

    @Override
    public void bind(KeyWordStateModel stateModel) {
        keyWordStateModel=stateModel;
    }

    @Override
    public void unbind() {
        keyWordStateModel=null;
    }

    @Override
    public void notifyAddKeyWord() {
        notifyItemInserted(keyWordStateModel.getKeyCount());
    }

    @Override
    public void notifyRemoveKeyWord(int index) {
        notifyItemRemoved(index);
    }

    public interface OnKeyClick{
        void onKeyClick(String key);
    }
    public interface OnDeleteKeyClick{
        void onDeleteKeyClick(String key);
    }
}

package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/9/13.
 */

public class KeyWordAdapter extends RecyclerView.Adapter<KeyWordAdapter.ViewHolder>{

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

    List<Item> keylist;
    OnKeyClick keyClick;
    OnDeleteKeyClick deleteKeyClick;
    boolean isEdit;

    public KeyWordAdapter(){
        keylist=new ArrayList<>();
        isEdit=false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.keylistitem,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item=keylist.get(position);
        if(isEdit){
            holder.showDeleteButton();
        }else{
            holder.hideDeleteButton();
        }
        holder.setKeyWord(item.getKey(),item.getColor());
        holder.setKeyClick(keyClick);
        holder.setDeleteKeyClick(deleteKeyClick);
    }

    public void add(String key,int color){
        Item item=new Item(key,color);
        keylist.add(item);
        notifyItemInserted(keylist.indexOf(item));
    }

    public void remove(String key){
        Item i=null;
        for(Item item:keylist){
            if(item.getKey().equals(key)){
                i=item;
            }
        }
        if(i !=null) {
            int index = keylist.indexOf(i);
            keylist.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemCount() {
        return keylist.size();
    }

    public void setKeylist(final List<KeyWord> list){
        keylist.clear();
        for(KeyWord k:list){
            Item item=new Item(k.getKeyword(),k.getColor());
            keylist.add(item);
        }
        notifyDataSetChanged();
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

    private class Item{
        String key;
        int color;

        Item(){

        }

        Item(String key,int color){
            this.key=key;
            this.color=color;
        }

        public int getColor() {
            return color;
        }

        public String getKey() {
            return key;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public interface OnKeyClick{
        void onKeyClick(String key);
    }
    public interface OnDeleteKeyClick{
        void onDeleteKeyClick(String key);
    }
}

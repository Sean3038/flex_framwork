package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.KeyFilterModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.KeyFilterDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/8/27.
 */

public class KeyFilterAdapter extends RecyclerView.Adapter<KeyFilterAdapter.ViewHolder> implements KeyFilterDataModel{

    List<Item> list;

    KeyFilterModel keyFilterModel;

    public KeyFilterAdapter(){
        list=new ArrayList<>();
    }

    @Override
    public void bind(KeyFilterModel stateModel) {
        this.keyFilterModel=stateModel;
        add(stateModel.getAllfilterkey());
    }

    @Override
    public void unbind() {
        keyFilterModel=null;
        add(new ArrayList<String>());
    }

    @Override
    public void notifyFilterChange() {
        add(keyFilterModel.getAllfilterkey());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkBox);
            textView=(TextView)itemView.findViewById(R.id.keyword);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.keyfilteritem,parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item item=list.get(position);
        holder.textView.setText(item.getKeyword());
        holder.checkBox.setChecked(item.isCheck());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b=((CheckBox) v).isChecked();
                holder.checkBox.setChecked(b);
                list.get(position).setCheck(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void add(List<String> keylist) {
        list.clear();
        for(String k:keylist){
            Item item=new Item();
            item.setCheck(true);
            item.setKeyword(k);
            list.add(item);
        }
        notifyDataSetChanged();
    }

    public void getSelectedKeyList(){
        List<String> keyList=new ArrayList<>();
        for(Item item:list){
            if(item.isCheck()){
                keyList.add(item.getKeyword());
            }
        }
        keyFilterModel.setfilterkey(keyList);
    }

    private class Item{
        private boolean check;
        private String keyword;

        public void setCheck(boolean check) {
            this.check = check;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public boolean isCheck() {
            return check;
        }

        public String getKeyword() {
            return keyword;
        }
    }
}

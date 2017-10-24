package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/10/23.
 */

public class LinkKeyAdapter extends RecyclerView.Adapter<LinkKeyAdapter.ViewHolder>{

    List<Item>  items;
    String noteurl;
    LinkKeySelectListener listener;

    LinkKeyAdapter(String noteurl,List<String> keywords,LinkKeySelectListener listener){
        this.items=new ArrayList<>();
        this.noteurl=noteurl;
        this.listener=listener;
        for(String key:keywords){
            Item item=new Item();
            item.setCheck(false);
            item.setKeyword(key);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.keyfilteritem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item=items.get(holder.getAdapterPosition());
        holder.keyword.setText(item.getKeyword());
        holder.check.setChecked(item.isCheck());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isCheck){
                    item.setCheck(false);
                    holder.check.setChecked(false);
                }else{
                    item.setCheck(true);
                    holder.check.setChecked(true);
                }
                listener.select(noteurl,getSelectKeyList());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<String> getSelectKeyList(){
        List<String> keylsit=new ArrayList<>();
        for(Item item:items){
            if(item.isCheck()){
                keylsit.add(item.getKeyword());
            }
        }
        return keylsit;
    }

    public class Item{
        boolean isCheck;
        String keyword;
        Item(){}

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        public boolean isCheck() {
            return isCheck;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox check;
        TextView keyword;

        public ViewHolder(View itemView) {
            super(itemView);
            check=(CheckBox)itemView.findViewById(R.id.checkBox);
            keyword=(TextView)itemView.findViewById(R.id.keyword);
        }
    }
    public interface LinkKeySelectListener{
        void select(String noteurl,List<String> keylist);
    }
}

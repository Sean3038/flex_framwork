package com.example.ffes.flex_framwork.noteview.searchmaterial.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2017/11/12.
 */

public class DepAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<String> list;

    private OnItemClick itemClick;

    public DepAdapter(Context context,OnItemClick itemClick){
        this.context=context;
        this.list=new ArrayList<>();
        this.itemClick=itemClick;
    }

    public class DepHolder extends RecyclerView.ViewHolder {
        public TextView item;
        public DepHolder(View itemView) {
            super(itemView);
            item=(TextView)itemView.findViewById(R.id.dep);
        }
    }

    @Override
    public DepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new DepHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DepHolder dh=(DepHolder) holder;
        dh.item.setText(list.get(position));
        dh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    itemClick.ItemSelect(list.get(position));
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<String> data){
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    interface OnItemClick{
        void ItemSelect(String item);
    }
}


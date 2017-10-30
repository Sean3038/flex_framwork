package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;

import java.util.List;

/**
 * Created by Ffes on 2017/10/29.
 */

public class DepAdapter extends RecyclerView.Adapter<DepAdapter.ViewHolder>{

    List<String> types;
    Context context;
    String selectedtype;

    DepAdapter(Context context,List<String> types){
        this.types=types;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.type.setText(types.get(holder.getAdapterPosition()));
        if(types.get(holder.getAdapterPosition()).equals(selectedtype)){
            holder.cardView.setBackgroundColor(Color.parseColor("#FFFFB6"));
        }else{
            holder.cardView.setBackgroundColor(Color.parseColor("#C8E0F0F7"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedtype=types.get(holder.getAdapterPosition());
                Log.d("Holder","Click"+selectedtype);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    public String getDep(){
        return selectedtype;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView type;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.cv);
            type=(TextView)itemView.findViewById(R.id.type);
        }
    }
}

package com.example.ffes.flex_framwork.noteview.personalspace.adapter;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Dep;

import java.util.List;


/**
 * Created by user on 2017/11/7.
 */

public class AlertDialogAdapter extends RecyclerView.Adapter<AlertDialogAdapter.ViewHolder>{
    private List<Dep> mData;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dep;
        CardView cv;
        private int mSelectedPos;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cv);
            dep = (TextView) v.findViewById(R.id.dep);
        }
    }



    public AlertDialogAdapter(Context context,List<Dep> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Dep dep_ = mData.get(position);
        holder.dep.setText(mData.get(position).getDep());
        holder.cv.setCardElevation(0);
        if (mData.get(position).isSelected) {//选中效果
            holder.cv.setCardBackgroundColor(Color.parseColor("#9ccbd9"));
            holder.dep.setTextColor(Color.parseColor("#013270"));
        } else {
            holder.cv.setCardBackgroundColor(Color.parseColor("#f7f7f7"));
            holder.dep.setTextColor(Color.parseColor("#8ba7cb"));
        }
        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vt) {
                for (Dep bean : mData) {
                    bean.isSelected = false;
                }
                mData.get(position).isSelected = true;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public AlertDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alertdialog_cardview, parent, false);
        AlertDialogAdapter.ViewHolder vh = new AlertDialogAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}


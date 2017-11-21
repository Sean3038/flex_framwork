package com.example.ffes.flex_framwork.noteview.searchmaterial.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.searchmaterial.data.Dep;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Ffes on 2017/8/30.
 */

public class AlertDialogAdapter extends RecyclerView.Adapter<AlertDialogAdapter.ViewHolder>{
    private List<Dep> mData;
    private Context mContext;
private OnClickDep listener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dep;
        private CardView cv;
        private int mSelectedPos;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cv);
            dep = (TextView) v.findViewById(R.id.dep);

        }

    }



    public AlertDialogAdapter(Context context, List<Dep> data,OnClickDep listener) {
        mData = data;
        mContext = context;
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Dep dep_ = mData.get(position);
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
                listener.onClick(dep_.getDep());
            }
            });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(dep_.getDep());
            }
        });
    }

    @Override
    public AlertDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alert_dialog_cardview, parent, false);
        AlertDialogAdapter.ViewHolder vh = new AlertDialogAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnClickDep{
        void onClick(String dep);
    }
}

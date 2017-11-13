package com.example.ffes.flex_framwork.noteview.personalspace.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.adapter.PageListAdapter;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Cover;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2017/11/7.
 */

public class CoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_HEAD =1;
    public static final int TYPE_VIEW=0;

    List<String> mData;
    Context mContext;
    OnCoverSelect callBack;

    CoverAdapter.ViewHolder lastholder;
    int lastindex=-1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;

        public ViewHolder(View v) {
            super(v);
            cover = (ImageView) v.findViewById(R.id.cover);
        }
    }


    public class HeadHolder extends RecyclerView.ViewHolder{
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    public CoverAdapter(Context context,OnCoverSelect callback,List<String> data) {
        mData = data;
        mContext = context;
        this.callBack=callback;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof CoverAdapter.HeadHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onAddCover();
                }
            });
        }
        if(holder instanceof CoverAdapter.ViewHolder){
            final String cover_data = mData.get(position-1);
            Picasso.with(mContext).load(cover_data).fit().centerCrop().error(R.drawable.account).into(((ViewHolder) holder).cover);
            holder.itemView.setScaleX(1);
            holder.itemView.setScaleY(1);
            if(lastindex==position){
                holder.itemView.setScaleX(1.2f);
                holder.itemView.setScaleY(1.2f);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //選到放大，並傳資料給Cover要變更圖片
                    if(lastholder!=null && lastholder!=holder && lastindex!=holder.getAdapterPosition()){
                        shrink(lastholder);
                    }
                    if(lastholder!=holder){
                        callBack.onSelectCover(cover_data);
                    }
                    scale((ViewHolder) holder);
                    lastholder= (ViewHolder) holder;
                    lastindex=holder.getAdapterPosition();
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            return new HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_addview,parent,false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cover_cardview, parent,false));
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }
        return TYPE_VIEW;
    }

    public void update(List<String> covers){
        mData.clear();
        mData.addAll(covers);
        notifyDataSetChanged();
    }

    public void scale(CoverAdapter.ViewHolder viewHolder){
        if(!(viewHolder.itemView.getScaleX() ==1.2f)) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator animatorx = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1, 1.2f);
            animatorx.setDuration(500);
            ObjectAnimator animatory = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1, 1.2f);
            animatory.setDuration(500);
            animatorSet.playTogether(animatorx, animatory);
            animatorSet.start();
        }
    }

    public void shrink(CoverAdapter.ViewHolder viewHolder){
        if(!(viewHolder.itemView.getScaleX() ==1)) {
            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator animatorx = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.2f, 1);
            animatorx.setDuration(500);
            ObjectAnimator animatory = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.2f, 1);
            animatory.setDuration(500);
            animatorSet.playTogether(animatorx, animatory);
            animatorSet.start();
        }
    }

    public interface OnCoverSelect{
        void onAddCover();
        void onSelectCover(String cover);
    }
}


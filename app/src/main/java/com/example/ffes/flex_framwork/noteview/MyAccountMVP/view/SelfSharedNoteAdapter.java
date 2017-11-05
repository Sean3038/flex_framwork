package com.example.ffes.flex_framwork.noteview.MyAccountMVP.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ffes on 2017/11/5.
 */

public class SelfSharedNoteAdapter extends RecyclerView.Adapter<SelfSharedNoteAdapter.ViewHolder>{

    List<SharedNote> list;

    SelfSharedNoteAdapter(List<SharedNote> list){
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView like;
        TextView look;
        TextView link;
        TextView comment;

        TextView title;
        TextView name;
        ImageView selfpicture;
        ImageView notepicture;

        public ViewHolder(View itemView) {
            super(itemView);
            like=(TextView)itemView.findViewById(R.id.like);
            look=(TextView)itemView.findViewById(R.id.look);
            link=(TextView)itemView.findViewById(R.id.link);
            comment=(TextView)itemView.findViewById(R.id.message);
            title=(TextView)itemView.findViewById(R.id.title);
            name=(TextView)itemView.findViewById(R.id.name);
            selfpicture=(ImageView)itemView.findViewById(R.id.selfpicture);
            notepicture=(ImageView)itemView.findViewById(R.id.note_picture);
        }

        public void loadSelfPicture(String imageurl){
            Picasso.with(itemView.getContext()).load(imageurl).resize(200,200).noPlaceholder().into(selfpicture);
        }

        public void loadNotePicture(String imageurl){
            Picasso.with(itemView.getContext()).load(imageurl).resize(200,200).noPlaceholder().into(notepicture);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.account_note_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SharedNote data=list.get(position);
        holder.comment.setText(""+data.getComment());
        holder.like.setText(""+data.getLike());
        holder.link.setText(""+data.getLink());
        holder.look.setText(""+data.getLook());
        holder.title.setText(""+data.getTitle());
        holder.name.setText(""+data.getName());
        holder.loadSelfPicture(data.getSelfpicture());
        holder.loadNotePicture(data.getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

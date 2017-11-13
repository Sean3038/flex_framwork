package com.example.ffes.flex_framwork.noteview.personalspace.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Note;
import com.example.ffes.flex_framwork.noteview.widget.Triangle;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by user on 2017/8/8.
 */

public class SelfNoteAdapter extends RecyclerView.Adapter<SelfNoteAdapter.ViewHolder>{

        List<Note> mData;
        Context mContext;

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView person_picture;
            ImageView note_picture;

            TextView person_account;
            TextView title;
            Triangle triangle;

            CardView cv;

            SwipeLayout swipeLayout;

            ImageButton edit;
            ImageButton delete;
            ImageButton share;

            public ViewHolder(View v) {
                super(v);
                cv = (CardView) v.findViewById(R.id.cv);
                person_picture = (ImageView) v.findViewById(R.id.selfphoto);
                note_picture = (ImageView) v.findViewById(R.id.note_picture);
                person_account = (TextView) v.findViewById(R.id.person_account);
                title = (TextView) v.findViewById(R.id.title);
                triangle = (Triangle) v.findViewById(R.id.triangle);
                swipeLayout=(SwipeLayout)v.findViewById(R.id.swipe_layout);
                edit=(ImageButton) v.findViewById(R.id.edit);
                delete = (ImageButton) v.findViewById(R.id.delete) ;
                share = (ImageButton) v.findViewById(R.id.share);
                swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                    @Override
                    public void onStartOpen(SwipeLayout layout) {

                    }

                    @Override
                    public void onOpen(SwipeLayout layout) {

                    }

                    @Override
                    public void onStartClose(SwipeLayout layout) {

                    }

                    @Override
                    public void onClose(SwipeLayout layout) {

                    }

                    @Override
                    public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                        cv.setRotationY(-leftOffset);
                    }

                    @Override
                    public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                    }
                });
            }

        }

        public SelfNoteAdapter(Context context, List<Note> data) {
            mData = data;
            mContext = context;
        }

        @Override
        public void onBindViewHolder(final SelfNoteAdapter.ViewHolder holder, final int position) {
            final Note note_ = mData.get(position);
            holder.person_account.setText(mData.get(position).getAccountName());
            holder.title.setText(mData.get(position).getTitle());
            holder.triangle.setColor(Color.RED);
            Picasso.with(mContext).load(mData.get(position).getUrl()).resize(50,50).error(R.drawable.account).into(holder.person_picture);
            Picasso.with(mContext).load(mData.get(position).getNoteURL()).resize(200,200).centerCrop().error(R.mipmap.ic_launcher).into(holder.note_picture);
            holder.cv.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
               //筆記本
                }
            });
            holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mData.remove(note_);
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyDataSetChanged();
                     }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //選封面圖片
                }
            });
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,AlertDialog.class);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public SelfNoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selfnote_cardview, parent, false);
            SelfNoteAdapter.ViewHolder vh = new SelfNoteAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

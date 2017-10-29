package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.data.LinkNote;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/23.
 */

public class LinkNoteAdapter extends RecyclerView.Adapter<LinkNoteAdapter.ViewHolder> implements LinkKeyAdapter.LinkKeySelectListener{

    List<LinkNote> linkNoteList;
    Map<String,List<String>> selectedlist;
    Context context;
    AuthRepository authRepository;


    public LinkNoteAdapter(Context context, List<LinkNote> linkNoteList){
        this.linkNoteList=linkNoteList;
        this.selectedlist=new HashMap<>();
        this.context=context;
        this.authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.linkitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LinkNote linkNote=linkNoteList.get(holder.getAdapterPosition());
        LinkKeyAdapter keyAdapter=new LinkKeyAdapter(linkNote.getId(),linkNote.getKeylist(),this);
        holder.keylist.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.keylist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        holder.keylist.setAdapter(keyAdapter);
        authRepository.getUser(linkNote.getName(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                holder.setName(data.getName());
                holder.loadSelfPhoto(data.getPhotoUrl());
            }

            @Override
            public void onFailure() {
                holder.setName("UnKnow");
                holder.loadSelfPhoto("zzzz");
            }
        });
        holder.setTitle(linkNote.getTitle());
        holder.loadCover(linkNote.getCoverurl());
    }

    @Override
    public int getItemCount() {
        return linkNoteList.size();
    }

    @Override
    public void select(String noteurl, List<String> keylist) {
        selectedlist.put(noteurl,keylist);
    }

    public Map<String,List<String>>  getSelectedNote(){
        return selectedlist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView cover;
        ImageView selfphoto;
        RecyclerView keylist;
        LinearLayout infromlayout;
        TextView title;
        TextView name;

        boolean isOpened=false;

        public ViewHolder(View itemView) {
            super(itemView);
            cover=(ImageView)itemView.findViewById(R.id.cover);
            selfphoto=(ImageView)itemView.findViewById(R.id.selfphoto);
            keylist=(RecyclerView)itemView.findViewById(R.id.keylist);
            infromlayout=(LinearLayout)itemView.findViewById(R.id.infromlayout);
            title=(TextView)itemView.findViewById(R.id.title);
            name=(TextView)itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isOpened){
                        keylist.setVisibility(View.GONE);
                        infromlayout.setVisibility(View.VISIBLE);
                        isOpened=false;
                    }else{
                        keylist.setVisibility(View.VISIBLE);
                        infromlayout.setVisibility(View.GONE);
                        isOpened=true;
                    }
                }
            });
        }

        public void loadCover(String imageurl){
            Picasso.with(context)
                    .load(imageurl)
                    .resize(500,500)
                    .placeholder(R.drawable.flex_icon)
                    .centerInside()
                    .into(cover);
        }

        public void loadSelfPhoto(String imageurl){
            Picasso.with(context)
                    .load(imageurl)
                    .resize(400,400)
                    .placeholder(R.drawable.account)
                    .centerInside()
                    .into(selfphoto);
        }

        public void setTitle(String title){
            this.title.setText(title);
        }

        public void setName(String name){
            this.name.setText(name);
        }
    }
}

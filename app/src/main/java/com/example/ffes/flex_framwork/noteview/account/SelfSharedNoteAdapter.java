package com.example.ffes.flex_framwork.noteview.account;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.linknote.view.ShareNoteBrowser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ffes on 2017/11/5.
 */

public class SelfSharedNoteAdapter extends RecyclerView.Adapter<SelfSharedNoteAdapter.ViewHolder>{

    List<SharedNote> list;

    NoteRepository noteRepository;

    SelfSharedNoteAdapter(List<SharedNote> list){
        this.list=list;
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
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
        CardView cv;

        ImageButton delete;

        public ViewHolder(View itemView) {
            super(itemView);
            like=(TextView)itemView.findViewById(R.id.like);
            look=(TextView)itemView.findViewById(R.id.look);
            link=(TextView)itemView.findViewById(R.id.link);
            comment=(TextView)itemView.findViewById(R.id.message);
            title=(TextView)itemView.findViewById(R.id.title);
            name=(TextView)itemView.findViewById(R.id.person_account);
            selfpicture=(ImageView)itemView.findViewById(R.id.selfpicture);
            notepicture=(ImageView)itemView.findViewById(R.id.note_picture);
            delete=(ImageButton)itemView.findViewById(R.id.delete);
            cv=(CardView)itemView.findViewById(R.id.cv);
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SharedNote data=list.get(position);
        holder.comment.setText(""+data.getComment());
        holder.like.setText(""+data.getLike());
        holder.link.setText(""+data.getLink());
        holder.look.setText(""+data.getLook());
        holder.title.setText(""+data.getTitle());
        holder.name.setText(""+data.getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteRepository.unshare(data.getUid(),data.getId());
                list.remove(data);
                notifyDataSetChanged();
            }
        });
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareNoteBrowser.start(v.getContext(),data.getId());
            }
        });
        holder.loadSelfPicture(data.getSelfpicture());
        holder.loadNotePicture(data.getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

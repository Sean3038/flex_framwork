package com.example.ffes.flex_framwork.noteview.EasyPersonalSpace;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteBrowserActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnUpLoadDataCallback;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.Note_data;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.widget.Triangle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ffes on 2017/10/29.
 */

public class PersonalNoteAdapter extends RecyclerView.Adapter<PersonalNoteAdapter.ViewHolder> {

    List<Note_data> note_dataList;
    Context context;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    PersonalNoteAdapter(Context context) {
        Log.d("PersonalNoteAdapter","START");
        this.context = context;
        note_dataList=new ArrayList<>();
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
        noteRepository.getPersonalSpaceAllNoteData(authRepository.getCurrentId(), new OnGetDataCallBack<List<Note_data>>() {
            @Override
            public void onSuccess(List<Note_data> data) {
                note_dataList.addAll(data);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.selfnote_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Note_data data=note_dataList.get(holder.getAdapterPosition());
        authRepository.getUser(data.getUid(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                holder.loadSelfPhoto(data.getPhotoUrl());
                holder.personAccount.setText(data.getAccountName());
            }

            @Override
            public void onFailure() {

            }
        });
        holder.loadCover(data.getCoverurl());
        holder.title.setText(data.getTitle());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteBrowserActivity.start(holder.itemView.getContext(),data.getNoteURL());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteEditorActivity.start(holder.itemView.getContext(),data.getNoteURL());
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteRepository.deleteNote(authRepository.getCurrentId(), data.getNoteURL());
                note_dataList.remove(data);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
        holder.triangle.setColor(Color.parseColor(data.getColor()));
    }

    @Override
    public int getItemCount() {
        return note_dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.edit)
        ImageButton edit;
        @BindView(R.id.share)
        ImageButton share;
        @BindView(R.id.delete)
        ImageButton delete;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.person_account)
        TextView personAccount;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.triangle)
        Triangle triangle;
        @BindView(R.id.selfphoto)
        ImageView selfphoto;
        @BindView(R.id.cv)
        CardView cv;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void loadCover(String imageurl){
            if(imageurl!=null){
                Picasso.with(itemView.getContext()).load(imageurl).placeholder(R.drawable.flex_icon).resize(400,400).centerInside().into(img);
            }
        }

        public void loadSelfPhoto(String imageurl){
            if(imageurl!=null){
                Picasso.with(itemView.getContext()).load(imageurl).placeholder(R.drawable.account).resize(400,400).centerInside().into(selfphoto);
            }
        }
    }


}

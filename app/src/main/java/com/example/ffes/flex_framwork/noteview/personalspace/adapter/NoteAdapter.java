package com.example.ffes.flex_framwork.noteview.personalspace.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.LinkNote;
import com.example.ffes.flex_framwork.noteview.data.SharedNote;
import com.example.ffes.flex_framwork.noteview.linknote.view.ShareNoteBrowser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2017/11/7.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<SharedNote> mData;
    Context mContext;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    public void update(List<SharedNote> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView person_picture;
        ImageView note_picture;

        TextView person_account;
        TextView title;
        TextView like;
        TextView look;
        TextView download;
        TextView message;

        CardView cv;

        SwipeLayout swipeLayout;

        ImageButton delete;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView) v.findViewById(R.id.cv);
            person_picture = (ImageView) v.findViewById(R.id.selfpicture);
            note_picture = (ImageView) v.findViewById(R.id.note_picture);
            person_account = (TextView) v.findViewById(R.id.person_account);
            title = (TextView) v.findViewById(R.id.title_content);
            like = (TextView) v.findViewById(R.id.like);
            look = (TextView) v.findViewById(R.id.look);
            download = (TextView) v.findViewById(R.id.link);
            message = (TextView) v.findViewById(R.id.message);
            swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe_layout);
            delete = (ImageButton) v.findViewById(R.id.delete);
            swipeLayout.setSwipeEnabled(false);
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
                    cv.setRotationY(-leftOffset/3);
                }
                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                }
            });
        }
    }

    public NoteAdapter(Context context,List<SharedNote> data) {
        mData = data;
        mContext = context;
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, final int position) {

        final SharedNote note = mData.get(position);
        holder.person_account.setText(note.getName());
        holder.title.setText(mData.get(position).getTitle());
        holder.like.setText(""+note.getLike());
        holder.look.setText(""+note.getLook());
        holder.download.setText(""+note.getLink());
        holder.message.setText(""+note.getComment());

        ImageView person_picture = holder.person_picture;
        Picasso.with(person_picture.getContext()).load(note.getSelfpicture()).resize(100,100).error(R.drawable.account).into(person_picture);

        ImageView note_picture = holder.note_picture;
        Picasso.with(note_picture.getContext()).load(note.getPhotoUrl()).fit().error(R.mipmap.ic_launcher).into(note_picture);

        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //連到筆記本
                ShareNoteBrowser.start(mContext,note.getId());
            }
        });

        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                notifyItemRemoved(position);
                mData.remove(note);
                noteRepository.deleteLinkNote(authRepository.getCurrentId(), note.getId());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_cardview , parent, false);
        ViewHolder vh = new NoteAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

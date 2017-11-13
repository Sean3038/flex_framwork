package com.example.ffes.flex_framwork.noteview.personalspace.adapter;

import android.content.Context;
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
import com.example.ffes.flex_framwork.noteview.EasyPersonalSpace.PersonalNoteAdapter;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.personalspace.data.Notebook;
import com.example.ffes.flex_framwork.noteview.personalspace.view.NoteBook_Note;
import com.example.ffes.flex_framwork.noteview.personalspace.view.NoteCover;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by user on 2017/11/7.
 */

public class NoteBookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEAD =1;
    public static final int TYPE_VIEW=0;

    List<Notebook> mData;
    Context mContext;

    ViewHolder lastholder;
    OnAddNoteBook callback;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    public NoteBookAdapter(Context context,OnAddNoteBook callback, List<Notebook> data) {
        mData = data;
        mContext = context;
        this.callback=callback;
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof NoteBookAdapter.HeadHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onAdd();
                }
            });
        }
        if(holder instanceof NoteBookAdapter.ViewHolder) {
            final ViewHolder v= (ViewHolder) holder;
            final Notebook noteBook = mData.get(position-1);
            v.title.setText(noteBook.getTitle());
            Picasso.with(mContext).load(noteBook.getCover()).fit().error(R.mipmap.ic_launcher).into(v.note_picture);
            v.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteBook_Note.startAction(mContext, noteBook.getId());

                }
            });
            v.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

            v.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {
                    if(lastholder!=null && lastholder!=v){
                        lastholder.swipeLayout.close();
                    }
                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    lastholder=v;
                }

                @Override
                public void onStartClose(SwipeLayout layout) {

                }

                @Override
                public void onClose(SwipeLayout layout) {
                }

                @Override
                public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                }

                @Override
                public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                }
            });

            v.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemRemoved(position-1);
                    mData.remove(noteBook);
                    noteRepository.deleteNoteBook(authRepository.getCurrentId(),noteBook.getId());
                    notifyDataSetChanged();
                }
            });
            v.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteCover.startAction(mContext, noteBook.getId());
                }
            });
        }
    }

    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            return new NoteBookAdapter.HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notebook_add, parent, false));
        }
        return new NoteBookAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notebook, parent, false));
    }

    public void update(List<Notebook> notebooks){
        mData.clear();
        mData.addAll(notebooks);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }
        return TYPE_VIEW;
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SwipeLayout swipeLayout;
        ImageButton edit;
        ImageButton delete;
        ImageView note_picture;
        TextView title;
        CardView cv;

        public ViewHolder(View v) {
            super(v);
            swipeLayout=(SwipeLayout)v.findViewById(R.id.swipe_layout);
            edit=(ImageButton) v.findViewById(R.id.edit);
            delete = (ImageButton) v.findViewById(R.id.delete) ;
            cv = (CardView) v.findViewById(R.id.cv);
            note_picture = (ImageView) v.findViewById(R.id.note_picture);
            title = (TextView) v.findViewById(R.id.title_content);
            swipeLayout.close();
        }

    }


    public class HeadHolder extends RecyclerView.ViewHolder{
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnAddNoteBook{
        void onAdd();
    }
}

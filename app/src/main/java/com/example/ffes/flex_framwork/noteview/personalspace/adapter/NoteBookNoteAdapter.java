package com.example.ffes.flex_framwork.noteview.personalspace.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
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
import com.example.ffes.flex_framwork.noteview.NoteBrowser.view.NoteBrowserActivity;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.api.NoteRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.widget.ShareNoteDialog;
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
 * Created by Ffes on 2017/11/13.
 */

public class NoteBookNoteAdapter extends RecyclerView.Adapter {
    public static final int TYPE_HEAD =1;
    public static final int TYPE_VIEW=0;

    List<PersonalNote> personalNote_List;
    FragmentManager fm;



    ShareNoteDialog.OnClickShare onClickShare;
    NoteBookNoteAdapter.OnAddNote callBack;

    NoteRepository noteRepository;
    AuthRepository authRepository;

    NoteBookNoteAdapter.ViewHolder lastholder;

    String bookurl;

    public NoteBookNoteAdapter(ShareNoteDialog.OnClickShare onClickShareck, NoteBookNoteAdapter.OnAddNote callBack, FragmentManager fm,String bookurl) {
        this.fm=fm;
        this.onClickShare=onClickShareck;
        this.callBack=callBack;
        this.bookurl=bookurl;
        personalNote_List =new ArrayList<>();
        noteRepository=new NoteRepository(FirebaseDatabase.getInstance(), FirebaseStorage.getInstance());
        authRepository=new AuthRepository(FirebaseAuth.getInstance(),FirebaseDatabase.getInstance());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_HEAD){
            return new NoteBookNoteAdapter.HeadHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.selfnote_add, parent, false));
        }
        return new NoteBookNoteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.selfnote_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NoteBookNoteAdapter.HeadHolder){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onAdd();
                }
            });
        }
        if(holder instanceof NoteBookNoteAdapter.ViewHolder) {
            final NoteBookNoteAdapter.ViewHolder h=(NoteBookNoteAdapter.ViewHolder)holder;
            final PersonalNote data = personalNote_List.get(position-1);
            authRepository.getUser(data.getUid(), new OnGetDataCallBack<User>() {
                @Override
                public void onSuccess(User data) {
                    h.loadSelfPhoto(data.getPhotoUrl());
                    h.personAccount.setText(data.getName());
                }

                @Override
                public void onFailure() {

                }
            });
            h.share.setEnabled(!data.isShare());
            h.loadCover(data.getCoverurl());
            h.title.setText(data.getTitle());
            h.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteBrowserActivity.start(h.itemView.getContext(),data.getUid(), data.getNoteURL());
                }
            });
            h.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteEditorActivity.start(h.itemView.getContext(), data.getNoteURL());
                }
            });

            h.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareNoteDialog.newInstance(data.getNoteURL(), onClickShare).show(fm, "ShareNoteDialog");
                    data.setShare(true);
                }
            });

            h.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteRepository.deleteNoteFromNoteBook(authRepository.getCurrentId(),bookurl, data.getNoteURL());
                    personalNote_List.remove(data);
                    notifyItemRemoved(h.getAdapterPosition());
                }
            });
            h.triangle.setColor(Color.parseColor(data.getColor()));
            h.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            h.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                @Override
                public void onStartOpen(SwipeLayout layout) {
                    if(lastholder!= null && lastholder!=h){
                        lastholder.swipeLayout.close();
                    }
                }

                @Override
                public void onOpen(SwipeLayout layout) {
                    lastholder=h;
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
        }
    }

    @Override
    public int getItemCount() {
        return personalNote_List.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }
        return TYPE_VIEW;
    }

    public void update(List<PersonalNote> personalNote_List){
        this.personalNote_List.clear();
        this.personalNote_List.addAll(personalNote_List);
        notifyDataSetChanged();
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
        @BindView(R.id.note_picture)
        ImageView img;
        @BindView(R.id.triangle)
        Triangle triangle;
        @BindView(R.id.selfphoto)
        ImageView selfphoto;
        @BindView(R.id.cv)
        CardView cv;
        @BindView(R.id.swipe_layout)
        SwipeLayout swipeLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            swipeLayout.close();
        }

        public void loadCover(String imageurl){
            if(imageurl!=null){
                Picasso.with(itemView.getContext()).load(imageurl).resize(200,200).centerInside().into(img);
            }
        }

        public void loadSelfPhoto(String imageurl){
            if(imageurl!=null){
                Picasso.with(itemView.getContext()).load(imageurl).resize(50,50).centerInside().into(selfphoto);
            }
        }
    }

    public class HeadHolder extends RecyclerView.ViewHolder{
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnAddNote{
        void onAdd();
    }
}

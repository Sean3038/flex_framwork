package com.example.ffes.flex_framwork.noteview.linknote.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.callback.OnGetDataCallBack;
import com.example.ffes.flex_framwork.noteview.api.AuthRepository;
import com.example.ffes.flex_framwork.noteview.data.Data.User;
import com.example.ffes.flex_framwork.noteview.linknote.data.Message;
import com.example.ffes.flex_framwork.noteview.widget.SupplyZoomDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.Manifest.permission.CAMERA;

/**
 * Created by user on 2017/11/20.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{



    List<Message> mData;
    Context mContext;
    FragmentManager fm;
    AuthRepository authRepository;
    String curretentid;
    public static final int TYPE_FOOT=1;//用id判斷是自己的留言或是別人的留言
    public static final int TYPE_VIEW=0;


    @Override
    public int getItemViewType(int position) {
        Message message=mData.get(position);
        if(curretentid.equals(message.getId())){
            return TYPE_VIEW;
        }else{
            return TYPE_FOOT;
        }
    }

    public MessageAdapter(Context context, FragmentManager fm, List<Message> data){
        mData = data;
        mContext = context;
        this.fm=fm;
        authRepository=new AuthRepository(FirebaseAuth.getInstance(), FirebaseDatabase.getInstance());
        curretentid=authRepository.getCurrentId();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOT){
            return new FootHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message,parent,false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FootHolder) {
            footBindValue((FootHolder) holder,position);
        }
        if(holder instanceof ViewHolder) {
            viewBindValue((ViewHolder) holder, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView account_picture;
        TextView my_message;
        ImageView photo_message;
        public ViewHolder(View itemView) {
            super(itemView);
            account_picture=(ImageView)itemView.findViewById(R.id.account_picture);
            my_message=(TextView) itemView.findViewById(R.id.my_message);
            photo_message=(ImageView)itemView.findViewById(R.id.photo_message);
        }
    }

    public class FootHolder extends RecyclerView.ViewHolder{
        ImageView other_picture;
        TextView other_message;
        ImageView photo_message;
        public FootHolder(View itemView) {
            super(itemView);
            other_picture=(ImageView)itemView.findViewById(R.id.other_picture);
            other_message=(TextView)itemView.findViewById(R.id.other_message);
            photo_message=(ImageView)itemView.findViewById(R.id.photo_message);
        }
    }

    private void viewBindValue(final ViewHolder vh, final int position){
        final Message message = mData.get(position);
        authRepository.getUser(message.getId(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                Picasso.with(vh.itemView.getContext()).load(data.getPhotoUrl()).resize(100,100).centerInside().into(vh.account_picture);
            }

            @Override
            public void onFailure() {

            }
        });
        if(message.getimageurl().length()!=0) {
            vh.photo_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupplyZoomDialog.newInstance(message.getimageurl()).show(fm,"SupplyZoomDialog");
                }
            });
            Picasso.with(vh.itemView.getContext()).load(message.getimageurl()).resize(800, 800).centerInside().into(vh.photo_message);
        }
        vh.my_message.setText(mData.get(position).getMessage());
    }

    private void footBindValue(final FootHolder vh, final  int position){
        final Message message = mData.get(position);
        authRepository.getUser(message.getId(), new OnGetDataCallBack<User>() {
            @Override
            public void onSuccess(User data) {
                Picasso.with(vh.itemView.getContext()).load(data.getPhotoUrl()).resize(100,100).centerInside().into(vh.other_picture);
            }

            @Override
            public void onFailure() {

            }
        });
        if(message.getimageurl().length()!=0) {
            vh.photo_message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupplyZoomDialog.newInstance(message.getimageurl()).show(fm,"SupplyZoomDialog");
                }
            });
            Picasso.with(vh.itemView.getContext()).load(message.getimageurl()).resize(800, 800).centerInside().into(vh.photo_message);
        }
        vh.other_message.setText(mData.get(position).getMessage());
    }

    public void add(Message message){
        mData.add(message);
        notifyItemInserted(getItemCount());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

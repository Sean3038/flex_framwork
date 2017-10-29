package com.example.ffes.flex_framwork.noteview.NoteBrowser.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.SupplyModel;
import com.example.ffes.flex_framwork.noteview.viewmodel.SupplyDataModel;
import com.example.ffes.flex_framwork.noteview.data.Supply;
import com.example.ffes.flex_framwork.noteview.widget.SupplyZoomDialog;
import com.squareup.picasso.Picasso;

/**
 * Created by Ffes on 2017/8/30.
 */

public class SupplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SupplyDataModel{


    public static final int SUPPLYITEM_TEXT=1;
    public static final int SUPPLYITEM_IMAGE=2;

    public static final int DISPLAY_HEIHT=800;
    public static final int DISPLAY_WIDTH=800;


    SupplyModel model;
    Context context;
    FragmentManager fm;
    boolean isEditor;

    @Override
    public void notifyAddSupply() {
        notifyItemInserted(model.getSupplyCount());
    }

    @Override
    public void notifyRemoveSupply(int index) {
        notifyItemRemoved(index);
    }

    @Override
    public void bind(SupplyModel supplyStateModel) {
        model=supplyStateModel;
    }

    @Override
    public void unbind() {
        model=null;
    }


    public class ImageViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        ImageView deletebtn;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            deletebtn=(ImageView)itemView.findViewById(R.id.deleteitembtn);
            if(isEditor){
                deletebtn.setVisibility(View.VISIBLE);
            }else{
                deletebtn.setVisibility(View.GONE);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupplyZoomDialog dialog=SupplyZoomDialog.newInstance(model.getSupply(getAdapterPosition()).getContent());
                    dialog.show(fm,"SupplyZoomDialog");
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.removeSupply(getAdapterPosition());
                }
            });
        }
    }

    public class TextViewHolder extends  RecyclerView.ViewHolder{

        TextView textView;
        ImageView deletebtn;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.textView);
            deletebtn=(ImageView)itemView.findViewById(R.id.deleteitembtn);
            if(isEditor){
                deletebtn.setVisibility(View.VISIBLE);
            }else{
                deletebtn.setVisibility(View.GONE);
            }
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model.removeSupply(getAdapterPosition());
                }
            });
        }
    }

    public SupplyAdapter(Context context, FragmentManager fm, boolean isEditor){
        this.context=context;
        this.fm=fm;
        this.isEditor=isEditor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case SUPPLYITEM_TEXT:
                return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.supplyitem_text,parent,false));
            case SUPPLYITEM_IMAGE:
                return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.supplyitem_image,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()){
            case SUPPLYITEM_IMAGE:
                bindImageViewHolder(holder,position);
                break;
            case SUPPLYITEM_TEXT:
                bindTextViewHolder(holder,position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return model.getSupplyCount();
    }

    @Override
    public int getItemViewType(int position) {
        return model.getSupply(position).getType();
    }

    private void bindTextViewHolder(RecyclerView.ViewHolder vh,int position){
        TextViewHolder tvh=(TextViewHolder)vh;
        Supply item=model.getSupply(position);
        tvh.textView.setText(item.getContent());
    }

    private void bindImageViewHolder(RecyclerView.ViewHolder vh,int position){
        ImageViewHolder tvh=(ImageViewHolder)vh;
        Supply item=model.getSupply(position);
        Picasso.with(context).load(item.getContent()).resize(DISPLAY_WIDTH,DISPLAY_HEIHT).centerInside().into(tvh.imageView);
    }
}

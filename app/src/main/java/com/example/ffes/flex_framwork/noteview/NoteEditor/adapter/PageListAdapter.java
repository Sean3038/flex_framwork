package com.example.ffes.flex_framwork.noteview.NoteEditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ffes on 2017/9/14.
 */

public class PageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_FOOT=1;
    public static final int TYPE_VIEW=0;

    List<PageContent> list;
    boolean isEditor;

    OnAddPageListener listener;
    OnSelectItemListener selectItemListener;
    public PageListAdapter(OnSelectItemListener listener){
        list=new ArrayList<>();
        isEditor=false;
        this.selectItemListener=listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOT){
            return new FootHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item,parent,false));
        }
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(holder instanceof FootHolder) {
                    footBindValue((FootHolder) holder);
                }
                if(holder instanceof ViewHolder) {
                    viewBindValue((ViewHolder) holder, position);
                }
    }


    @Override
    public int getItemCount() {
        return list.size()+1;
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView pageImage;
        ImageView delete;
        public ViewHolder(View itemView) {
            super(itemView);
            pageImage=(ImageView)itemView.findViewById(R.id.pageImage);
            delete=(ImageView)itemView.findViewById(R.id.delete);
        }
    }

    public class FootHolder extends RecyclerView.ViewHolder{
        ImageView add_btn;
        public FootHolder(View itemView) {
            super(itemView);
            add_btn=(ImageView)itemView.findViewById(R.id.add_btn);
        }
    }

    private void viewBindValue(final ViewHolder vh, int position){
        final PageContent page=list.get(position);
        Picasso.with(vh.itemView.getContext()).load(page.getPageNoteUrl()).into(vh.pageImage);
        if(isEditor){
            vh.delete.setVisibility(View.VISIBLE);
        }else{
            vh.delete.setVisibility(View.GONE);
        }
        vh.pageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItemListener.onSelect(vh.getAdapterPosition());
            }
        });
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItemListener.onDelete(page.getPage(),vh.getAdapterPosition());
                list.remove(vh.getAdapterPosition());
                notifyItemRemoved(vh.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    private void footBindValue(FootHolder vh){
        vh.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onAddPage();
                }
            }
        });
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
        notifyDataSetChanged();
    }

    public void setPageList(List<PageContent> pageContents){
        this.list.clear();
        this.list.addAll(pageContents);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==getItemCount()-1){
            return TYPE_FOOT;
        }
        return TYPE_VIEW;
    }


    public void setListener(OnAddPageListener listener) {
        this.listener = listener;
    }

    public interface OnAddPageListener{
        void onAddPage();
    }

    public interface OnSelectItemListener{
        void onSelect(int position);
        void onDelete(int page,int position);
    }
}

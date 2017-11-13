package com.example.ffes.flex_framwork.noteview.personalspace.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.data.Data.PersonalNote;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ffes on 2017/11/12.
 */

public class SelectNoteAdapter extends RecyclerView.Adapter<SelectNoteAdapter.ViewHolder>{

    List<Item> items;
    Context context;

    SelectNoteAdapter(Context context){
        this.context=context;
        items=new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.selectnote_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item=items.get(holder.getAdapterPosition());
        Picasso.with(context).load(item.getNote().getCoverurl()).resize(400,400).into(holder.img);
        holder.check.setChecked(item.getFlag());
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getFlag()){
                    item.setFlag(false);
                }else{
                    item.setFlag(true);
                }
            }
        });
        holder.title.setText(item.getNote().getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update(List<PersonalNote> notes){
        items.clear();
        for(PersonalNote note:notes){
            Item item=new Item();
            item.setFlag(false);
            item.setNote(note);
            items.add(item);
        }
        notifyDataSetChanged();
    }

    public List<PersonalNote> getSelectedItem(){
        List<PersonalNote> notes=new ArrayList<>();
        for(Item item:items){
            if(item.getFlag()) {
                notes.add(item.getNote());
            }
        }
        return notes;
    }

    public class Item{
        boolean flag;
        PersonalNote note;
        Item(){}

        public PersonalNote getNote() {
            return note;
        }

        public boolean getFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setNote(PersonalNote note) {
            this.note = note;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox check;
        TextView title;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            check=(CheckBox)itemView.findViewById(R.id.check);
            title=(TextView)itemView.findViewById(R.id.title);
            img=(ImageView)itemView.findViewById(R.id.note_picture);
        }
    }
}

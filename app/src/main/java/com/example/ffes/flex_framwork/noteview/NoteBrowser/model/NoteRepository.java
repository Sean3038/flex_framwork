package com.example.ffes.flex_framwork.noteview.NoteBrowser.model;

import android.graphics.Color;
import android.graphics.RectF;

import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.Arrays;
import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteRepository {

    Note data;
    private static  NoteRepository repository;
    private NoteRepository(){
        data=new Note("dsaf", Arrays.asList(
                new Page(new PageContent(1,"http://i.imgur.com/DvpvklR.png"),Arrays.asList(new Supply(1,1,"I'm data")),Arrays.asList(new KeyWord("Strategy",new RectF(0,0,200,200),Color.parseColor("#cc66ee99")),new KeyWord("Memento",new RectF(250,250,400,400), Color.parseColor("#ccffeeee")),new KeyWord("Observer",new RectF(600,600,900,900),Color.parseColor("#cc00eeff")),new KeyWord("Iterator",new RectF(0,0,60,60), Color.parseColor("#cc55eeee"))),null),
                new Page(new PageContent(2,"https://i.imgur.com/tGbaZCY.jpg"),Arrays.asList(new Supply(1,1,"I'm data2"),new Supply(2,2,"https://i.imgur.com/ME4ewjl.jpg"),new Supply(3,2,"https://i.imgur.com/tGbaZCY.jpg"),new Supply(4,1,"this is test 3")),Arrays.asList(new KeyWord("State",new RectF(300,250,600,700),Color.parseColor("#aa000eff"))),null)
        ),"coverurl","Pattern","#f6b9b9","authorID","20171029","20170648");


    }

    public static NoteRepository getInstance(){
        if(repository==null){
            repository=new NoteRepository();
        }
        return repository;
    }

    public Observable<Note> getNote(String id){
        return Observable.fromCallable(new Callable<Note>() {
            @Override
            public Note call() throws Exception {
                return data;
            }
        });
    }
}

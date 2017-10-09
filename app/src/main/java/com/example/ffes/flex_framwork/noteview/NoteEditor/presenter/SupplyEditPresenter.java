package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteEditor.SupplyEditorContract;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Supply;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Ffes on 2017/9/19.
 */

public class SupplyEditPresenter implements SupplyEditorContract.Presenter{
    SupplyEditorContract.View view;
    NoteRepository repository;

    public SupplyEditPresenter(SupplyEditorContract.View view, NoteRepository repository){
        this.view=view;
        this.repository=repository;
    }


    @Override
    public void addPhoto(String noteUrl, int page, final String photeUrl) {
        repository.getNote(noteUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        Supply supply=new Supply(5,2,photeUrl);
                        view.addSupply(supply);
                    }
                });
    }

    @Override
    public void addMessage(String noteUrl, int page, final String message) {
        if(!message.equals("")) {
            repository.getNote(noteUrl)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Note>() {
                        @Override
                        public void accept(Note note) throws Exception {
                            view.clearInput();
                            Supply supply = new Supply(5, 1, message);
                            view.addSupply(supply);
                        }
                    });
        }
    }
}

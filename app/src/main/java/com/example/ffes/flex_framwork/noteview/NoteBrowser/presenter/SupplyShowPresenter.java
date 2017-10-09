package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.SupplyShowContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.util.NoteDecomposeUtil;
import com.example.ffes.flex_framwork.noteview.data.Note;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ffes on 2017/9/25.
 */

public class SupplyShowPresenter implements SupplyShowContract.Presenter {

    SupplyShowContract.View view;
    NoteRepository repository;

    public SupplyShowPresenter(SupplyShowContract.View view,NoteRepository repository){
        this.view=view;
        this.repository=repository;
    }

    @Override
    public void loadSupply(String noteUrl, final int page) {
        repository.getNote(noteUrl)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Note>() {
                        @Override
                        public void accept(Note note) throws Exception {
                            view.setSupplyList(NoteDecomposeUtil.getSupplyList(note,page));
                        }
                    });
    }
}

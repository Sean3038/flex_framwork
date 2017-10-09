package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.util.NoteDecomposeUtil;
import com.example.ffes.flex_framwork.noteview.NoteEditor.KeyEditorContract;
import com.example.ffes.flex_framwork.noteview.data.Note;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ffes on 2017/9/25.
 */

public class KeyEditorPresenter implements KeyEditorContract.Presenter {

    KeyEditorContract.View view;
    NoteRepository repository;

    public KeyEditorPresenter(KeyEditorContract.View view){
        this.view=view;
        repository= NoteRepository.getInstance();
    }

    @Override
    public void loadData(String noteUrl) {
        repository.getNote(noteUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        view.setNoteList(NoteDecomposeUtil.getPageList(note));
                    }
                });
        ;
    }

    @Override
    public void clickAdd() {
        view.showAddKeyDialog();
    }
}

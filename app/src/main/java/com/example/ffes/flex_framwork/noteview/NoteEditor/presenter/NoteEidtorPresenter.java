package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import android.graphics.Color;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.util.NoteDecomposeUtil;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteEditorContract;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.example.ffes.flex_framwork.noteview.widget.NoteTitleDialogFragment;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ffes on 2017/9/18.
 */

public class NoteEidtorPresenter implements NoteEditorContract.Presenter{

    NoteEditorContract.View view;
    NoteRepository repository;


    public NoteEidtorPresenter(NoteEditorContract.View view, NoteRepository repository){
        this.view=view;
        this.repository=repository;
    }

    @Override
    public void loadData(String noteUrl) {
        repository.getNote(noteUrl)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io()).subscribe(new Consumer<Note>() {
            @Override
            public void accept(Note note) throws Exception {
                List<PageContent> pageContents= NoteDecomposeUtil.getPageContents(note);
                view.setNoteMenu(pageContents);
                view.setTitleDetail(note.getTitle(),Color.parseColor(note.getColor()));
                view.setPageState(1,pageContents.size());
            }
        });
    }

    @Override
    public void modifyNoteDetail(String noteUrl, String name, int color) {

    }

    @Override
    public void removePage(String noteUrl, int page) {

    }

    @Override
    public void addPage(String noteUrl, int page) {

    }

    @Override
    public void getTitleDetail(String noteUrl) {
        repository.getNote(noteUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new Consumer<Note>() {
            @Override
            public void accept(Note note) throws Exception {
                view.showTitleEditor(note.getTitle(), Color.parseColor(note.getColor()));
            }
        });
    }
}

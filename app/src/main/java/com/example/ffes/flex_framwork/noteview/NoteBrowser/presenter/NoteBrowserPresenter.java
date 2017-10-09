package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.util.NoteDecomposeUtil;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteBrowserPresenter implements NoteBrowserContract.Presenter{

    NoteBrowserContract.View view;
    NoteRepository noteRepository;

    public NoteBrowserPresenter(NoteBrowserContract.View view,NoteRepository noteRepository){
        this.view=view;
        this.noteRepository=noteRepository;
        loadNote();
    }

    @Override
    public void loadNote() {
        String id="test";
        noteRepository.getNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        view.setKeyList(NoteDecomposeUtil.getKeyWordList(note));
                        List<Integer> pagelist=new ArrayList<>();
                        List<Page> pages=NoteDecomposeUtil.getPages(note);
                        for(Page p:pages) {
                            pagelist.add(p.getPageContent().getPage());
                        }
                        view.setNotePage(pagelist);
                        view.setTitle(note.getTitle());
                        view.setTotalPage(NoteDecomposeUtil.getPages(note).size());
                    }
                });
    }

    @Override
    public void onOpenKeyList() {
        view.showKeyList();
    }

    @Override
    public void onOpenSupply(final List<String> keyList) {
        String id="test";
        noteRepository.getNote(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Note>() {
                @Override
                public void accept(Note note) throws Exception {
                    view.showSupply("dsfa",NoteDecomposeUtil.getPageByKeyWord(note,keyList));
                }
            });
    }

    @Override
    public void onOpenQA() {
        view.showQA();
    }

    @Override
    public void onSelectKeyWord(final List<String> keyList) {
        String id="test";
        noteRepository.getNote(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        view.setNotePage(NoteDecomposeUtil.getPageByKeyWord(note,keyList));
                    }
                });
    }
}

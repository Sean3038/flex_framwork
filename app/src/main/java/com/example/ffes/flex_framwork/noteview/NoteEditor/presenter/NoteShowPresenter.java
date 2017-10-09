package com.example.ffes.flex_framwork.noteview.NoteEditor.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.model.NoteRepository;
import com.example.ffes.flex_framwork.noteview.NoteBrowser.util.NoteDecomposeUtil;
import com.example.ffes.flex_framwork.noteview.NoteEditor.NoteShowContract;
import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ffes on 2017/9/25.
 */

public class NoteShowPresenter implements NoteShowContract.Presenter{

    NoteShowContract.View view;
    NoteRepository repository;

    public NoteShowPresenter(NoteShowContract.View view){
        this.view=view;
        repository=NoteRepository.getInstance();
    }

    @Override
    public void LoadData(String NoteUrl, final int page) {
        repository.getNote("NoteUrl")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Note>() {
                    @Override
                    public void accept(Note note) throws Exception {
                        Page p=NoteDecomposeUtil.getPage(note,page);
                        view.setKeyData(p.getPageContent().getPageNoteUrl(),p.getKeyWordList());
                    }
                });
    }

    @Override
    public void saveAllKeyWord(int page, List<KeyWord> keyWords) {

    }

    @Override
    public void addKeyWordFrame(int page, KeyWord keyWord) {
    }

    @Override
    public void removeKeyWordFrame(int page, String key) {

    }
}

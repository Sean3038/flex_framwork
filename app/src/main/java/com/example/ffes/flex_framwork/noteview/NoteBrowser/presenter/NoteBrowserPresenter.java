package com.example.ffes.flex_framwork.noteview.NoteBrowser.presenter;

import com.example.ffes.flex_framwork.noteview.NoteBrowser.NoteBrowserContract;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.NoteLoadModel;
import java.util.List;



/**
 * Created by Ffes on 2017/8/27.
 */

public class NoteBrowserPresenter implements NoteBrowserContract.Presenter{

    NoteBrowserContract.View view;
    NoteLoadModel model;

    public NoteBrowserPresenter(NoteBrowserContract.View view, NoteLoadModel model){
        this.view=view;
        this.model=model;
        loadNote();
    }

    @Override
    public void loadNote() {
//                        view.setKeyList(NoteDecomposeUtil.getKeyWordList(note));
//                        for(Page p:pages) {
//                            pagelist.add(p.getPageContent().getPage());
//                        }
//                        view.setNotePage(pagelist);
//                        view.setTitle(note.getTitle());
//                        view.setTotalPage(NoteDecomposeUtil.getPages(note).size());
    }

    @Override
    public void onOpenKeyList() {
        view.showKeyList();
    }

    @Override
    public void onOpenSupply(final List<String> keyList) {
        //view.showSupply("dsfa",NoteDecomposeUtil.getPageByKeyWord(note,keyList));
    }

    @Override
    public void onOpenQA() {
        view.showQA();
    }

    @Override
    public void onSelectKeyWord(final List<String> keyList) {

        //view.setNotePage(NoteDecomposeUtil.getPageByKeyWord(note,keyList));
    }
}

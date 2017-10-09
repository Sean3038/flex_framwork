package com.example.ffes.flex_framwork.noteview.NoteBrowser.util;

import com.example.ffes.flex_framwork.noteview.data.KeyWord;
import com.example.ffes.flex_framwork.noteview.data.Note;
import com.example.ffes.flex_framwork.noteview.data.Page;
import com.example.ffes.flex_framwork.noteview.data.PageContent;
import com.example.ffes.flex_framwork.noteview.data.QA;
import com.example.ffes.flex_framwork.noteview.data.Supply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ffes on 2017/8/28.
 */

public class NoteDecomposeUtil {

    private NoteDecomposeUtil(){};

    public static List<Page> getPages(Note note){
        return note.getPagesList();
    }

    public static Page getPage(Note note,int page){
        List<Page> pageList=note.getPagesList();
        for(Page p:pageList){
            if(p.getPageContent().getPage()==page){
                return p;
            }
        }
        return null;
    }

    public static List<String> getKeyWordList(Note note){
        List<String> list=new ArrayList<>();
        for(Page p:note.getPagesList()){
            for(KeyWord k:p.getKeyWordList()){
                if(!list.contains(k.getKeyword())){
                    list.add(k.getKeyword());
                }
            };
        }
        Collections.sort(list,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return list;
    }

    public static List<String> getKeyWordList(Page page){
        List<String> list=new ArrayList<>();
        for(KeyWord k:page.getKeyWordList()){
            list.add(k.getKeyword());
        };
        return list;
    }

    public static List<Supply> getSupplyList(Note note,int page){
        List<Supply> list=new ArrayList<>();
        for(Page p:note.getPagesList()){
            if(p.getPageContent().getPage()==page){
                return p.getSupply();
            }
        }
        return null;
    }

    public static List<Integer> getPageByKeyWord(Note note,List<String> filter){
        List<Integer> pages=new ArrayList<>();
        for(Page p:note.getPagesList()){
            List<String> list=getKeyWordList(p);
            list.retainAll(filter);
            if(!list.isEmpty()){
                pages.add(p.getPageContent().getPage());
            }
        }
        return pages;
    }

    public static List<Integer> getPageList(Note note){
        List<Integer> pagelist=new ArrayList<>();
        for(Page p:note.getPagesList()){
            pagelist.add(p.getPageContent().getPage());
        }
        return pagelist;
    }

    public static List<PageContent> getPageContents(Note note){
        List<PageContent> list=new ArrayList<>();
        for(Page page:note.getPagesList()){
            list.add(page.getPageContent());
        }
        return list;
    }
}

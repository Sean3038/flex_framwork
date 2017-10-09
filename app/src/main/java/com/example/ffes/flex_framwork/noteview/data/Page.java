package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/8/28.
 */

public class Page {
    PageContent pageContent;
    List<Supply> supplyLiist;
    List<KeyWord> keyWordList;
    List<QA> qaList;

    public Page(){}

    public Page(PageContent pageContent,List<Supply> supply,List<KeyWord> keyWordList,List<QA> qaList){
        this.pageContent=pageContent;
        this.keyWordList=keyWordList;
        this.supplyLiist=supply;
        this.qaList=qaList;
    }

    public void setPageContent(PageContent pageContent) {
        this.pageContent = pageContent;
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    public void setKeyWordList(List<KeyWord> keyWordList) {
        this.keyWordList = keyWordList;
    }

    public void setSupplyList(List<Supply> supplyLiist) {
        this.supplyLiist = supplyLiist;
    }

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    public List<KeyWord> getKeyWordList() {
        return keyWordList;
    }

    public List<QA> getQaList() {
        return qaList;
    }

    public  List<Supply> getSupply() {
        return supplyLiist;
    }
}

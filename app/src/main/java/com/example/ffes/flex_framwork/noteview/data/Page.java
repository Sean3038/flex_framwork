package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/8/28.
 */

public class Page {
    String imageurl;
    List<Supply> supplyLiist;
    List<KeyWord> keyWordList;
    List<QA> qaList;

    public Page(){}

    public Page(String imageurl,List<Supply> supply,List<KeyWord> keyWordList,List<QA> qaList){
        this.imageurl=imageurl;
        this.keyWordList=keyWordList;
        this.supplyLiist=supply;
        this.qaList=qaList;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
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

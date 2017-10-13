package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/8/28.
 */

public class Page {
    String imageurl;
    List<Supply> supplylist;
    List<KeyWord> keywordlist;
    List<QA> qalist;

    public Page(){}

    public Page(String imageurl,List<Supply> supplylist,List<KeyWord> keywordlist,List<QA> qalist){
        this.imageurl=imageurl;
        this.keywordlist =keywordlist;
        this.supplylist=supplylist;
        this.qalist=qalist;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getimageurl() {
        return imageurl;
    }

    public void setkeywordlist(List<KeyWord> keywordlist) {
        this.keywordlist = keywordlist;
    }

    public void setsupplylist(List<Supply> supplylist) {
        this.supplylist = supplylist;
    }

    public void setqalist(List<QA> qalist) {
        this.qalist = qalist;
    }

    public List<KeyWord> getkeywordlist() {
        return keywordlist;
    }

    public List<QA> getqalist() {
        return qalist;
    }

    public  List<Supply> getsupplylist() {
        return supplylist;
    }
}

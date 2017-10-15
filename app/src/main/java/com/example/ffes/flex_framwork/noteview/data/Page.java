package com.example.ffes.flex_framwork.noteview.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/8/28.
 */

public class Page {
    String id;
    String imageurl;
    List<Supply> supplylist;
    Map<String,KeyWord> keywordlist;
    List<QA> qalist;

    public Page(){}

    public Page(String id,String imageurl,List<Supply> supplylist,Map<String,KeyWord>  keywordlist,List<QA> qalist){
        this.id=id;
        this.imageurl=imageurl;
        this.keywordlist =keywordlist;
        this.supplylist=supplylist;
        this.qalist=qalist;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setimageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getimageurl() {
        return imageurl;
    }

    public void setkeywordlist(Map<String,KeyWord>  keywordlist) {
        this.keywordlist = keywordlist;
    }

    public void setsupplylist(List<Supply> supplylist) {
        this.supplylist = supplylist;
    }

    public void setqalist(List<QA> qalist) {
        this.qalist = qalist;
    }

    public Map<String,KeyWord>  getkeywordlist() {
        return keywordlist;
    }

    public List<QA> getqalist() {
        return qalist;
    }

    public  List<Supply> getsupplylist() {
        return supplylist;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        map.put("imageurl",imageurl);

        Map<String,Object> supply=new HashMap<>();
        int c=0;
        for(Supply s:supplylist){
            supply.put(""+c,s.toMap());
            c++;
        }

        map.put("supplylist",supply);

        Map<String,Object> qa=new HashMap<>();
        c=0;
        for(QA q:qalist){
            qa.put(""+c,q.toMap());
            c++;
        }

        map.put("qalist",qa);

        Map<String,Object> keyword=new HashMap<>();
        for(String k:keywordlist.keySet()){
            keyword.put(k,keywordlist.get(k).toMap());
        }

        map.put("keywordlist",keyword);
        return map;
    }
}

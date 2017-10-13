package com.example.ffes.flex_framwork.noteview.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ffes on 2017/10/13.
 */

public class Post {
    String authorid;
    String cover;
    String createat;
    Map<String,Page> notecontent;
    Map<Integer,String> pagelist;
    TitleDetail titledetail;
    String updateat;

    Post(){}

    Post(String authorid,String cover,String createat,Map<String,Page> notecontent,Map<Integer,String> pagelist,TitleDetail titledetail,String updateat){

    }


}

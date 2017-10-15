package com.example.ffes.flex_framwork.noteview.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ffes on 2017/8/29.
 */

public class Supply implements Serializable {
    int type;
    String content;

    public Supply(){}

    public Supply(int type, String content){
        this.type=type;
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("type",type);
        map.put("content",content);

        return map;
    }
}

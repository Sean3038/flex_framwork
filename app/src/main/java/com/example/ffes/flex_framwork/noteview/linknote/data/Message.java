package com.example.ffes.flex_framwork.noteview.linknote.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/11/20.
 */

public class Message {

    private String imageurl;
    private String id;
    private String message;

    public Message(){}

    public Message(String imageurl, String id, String message) {
        this.imageurl = imageurl;
        this.id = id;
        this.message = message;
    }

    public String getimageurl(){
        return imageurl;
    }

    public void setimageurl(String imageurl){
        this.imageurl = imageurl;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<>();
        map.put("uid",id);
        map.put("message",message);
        map.put("photo",imageurl);

        return map;

    }
}

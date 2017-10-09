package com.example.ffes.flex_framwork.noteview.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ffes on 2017/8/29.
 */

public class Supply implements Serializable {
    int supplyId;
    int type;
    String content;

    public Supply(){}

    public Supply(int supplyId,int type, String content){
        this.supplyId=supplyId;
        this.type=type;
        this.content=content;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
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
}

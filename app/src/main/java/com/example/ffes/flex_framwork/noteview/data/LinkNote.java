package com.example.ffes.flex_framwork.noteview.data;

import java.util.List;

/**
 * Created by Ffes on 2017/10/23.
 */

public class LinkNote {
    String id;
    String name;
    String title;
    List<String> keylsit;

    public LinkNote(){}

    public String getId() {
        return id;
    }

    public List<String> getKeylist() {
        return keylsit;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKeylsit(List<String> keylsit) {
        this.keylsit = keylsit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.example.ffes.flex_framwork.noteview.searchmaterial.data;

/**
 * Created by Ffes on 2017/8/31.
 */

public class Dep {
    private String dep;
    public boolean isSelected;

    public Dep(String dep, boolean selected) {
       this.dep = dep;
        isSelected = selected;
    }

    public String getDep(){
        return dep;
    }

    public void setDep(String dep){
        this.dep = dep;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

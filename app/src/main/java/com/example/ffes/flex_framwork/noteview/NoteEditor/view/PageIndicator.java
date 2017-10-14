package com.example.ffes.flex_framwork.noteview.NoteEditor.view;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ffes.flex_framwork.R;
import com.example.ffes.flex_framwork.noteview.NoteEditor.model.statemodel.PageModel;
import com.example.ffes.flex_framwork.noteview.NoteEditor.viewmodel.PageDataModel;

/**
 * Created by Ffes on 2017/10/13.
 */

public class PageIndicator implements PageDataModel {

    TextView currentpage;
    TextView totalpage;

    PageModel stateModel;

    PageIndicator(ViewGroup viewGroup){
        currentpage=(TextView)viewGroup.findViewById(R.id.currentpage);
        totalpage=(TextView)viewGroup.findViewById(R.id.totalpage);
    }

    @Override
    public void notifyAddPage() {
        setPageState(stateModel.getCurrentPage(), stateModel.getTotalPage());
    }

    @Override
    public void notifyRemovePage(int index) {
//        if(stateModel.getTotalPage()==0) {
//            supply_btn.setVisibility(View.GONE);
//            notifyNoPage.setVisibility(View.VISIBLE);
//        }
        setPageState(stateModel.getCurrentPage(), stateModel.getTotalPage());
    }

    @Override
    public void notifyCurrentPage(int page) {
        setPageState(stateModel.getCurrentPage(), stateModel.getTotalPage());
    }

    @Override
    public void bind(PageModel pageStateModel) {
        stateModel=pageStateModel;
        setPageState(stateModel.getCurrentPage(), stateModel.getTotalPage());
    }

    @Override
    public void unbind() {
        stateModel=null;
        setPageState(0, 0);
    }

    public void setPageState(int currentpage, int totalpage) {
        this.totalpage.setText(totalpage+"");
        this.currentpage.setText(currentpage+"");
    }
}

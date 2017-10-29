package com.example.ffes.flex_framwork;

import com.example.ffes.flex_framwork.noteview.widget.NoteView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


/**
 * Created by Ffes on 2017/9/21.
 */
@RunWith(JUnit4.class)
public class NoteViewTest {
    @Mock
    NoteView noteView;

    @Before
    public void initView(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNoteAdd(){
        Mockito.verify(noteView,Mockito.times(0)).setEdit(true);
    }
}

package com.example.ffes.flex_framwork;


import android.support.test.runner.AndroidJUnit4;

import com.example.ffes.flex_framwork.noteview.widget.NoteView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Ffes on 2017/9/21.
 */
@RunWith(AndroidJUnit4.class)
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

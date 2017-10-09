package com.example.ffes.flex_framwork;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ffes.flex_framwork.noteview.NoteEditor.view.NoteEditorActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Ffes on 2017/9/21.
 */
@RunWith(AndroidJUnit4.class)
public class NoteEditorActivityTest{
    public ActivityTestRule<NoteEditorActivity> rule= new ActivityTestRule<>(NoteEditorActivity.class);

    @Test
    public void onCreate(){

    }
}

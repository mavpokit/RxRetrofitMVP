package com.mavpokit.rxretrofitmvp.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
/**
 * Created by Alex on 01.09.2016.
 */
public class AnswersFragmentTest extends BaseTest{

    @Inject
    IAnswersPresenter presenter;

    //QuestionsFragment questionsFragment=new QuestionsFragment();
    AnswersFragment answersFragment;

    Bundle bundle;

    MainActivity activity;

    private static final String LINK="https://github.com";
    private static final Question question=new Question(LINK,"title",1,"2");


    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        answersFragment=AnswersFragment.getInstance(question);
        bundle=Bundle.EMPTY;
        activity= Robolectric.setupActivity(MainActivity.class);
        //activity= Robolectric.buildActivity(MainActivity.class).create().get();
        answersFragment.onCreate(bundle);
        System.out.println("setup");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnCreateView() throws Exception {
        answersFragment.onCreateView(LayoutInflater.from(activity),(ViewGroup)activity.findViewById(R.id.container),null);
        verify(presenter).onCreateView();
    }

    @Test
    public void testOnCreate() throws Exception {
        verify(presenter).onCreate(answersFragment,question);
    }

    @Test
    public void testOnStop() throws Exception {
        answersFragment.onStop();
        verify(presenter).onStop();
    }
}
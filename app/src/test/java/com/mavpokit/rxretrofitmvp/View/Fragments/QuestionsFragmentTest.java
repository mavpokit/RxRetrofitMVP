package com.mavpokit.rxretrofitmvp.View.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavpokit.rxretrofitmvp.BaseTest;
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
 * Created by Alex on 31.08.2016.
 */
public class QuestionsFragmentTest extends BaseTest{

    @Inject
    IQuestionsPresenter presenter;

    //QuestionsFragment questionsFragment=new QuestionsFragment();
    QuestionsFragment questionsFragment;

    Bundle bundle;

    MainActivity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        questionsFragment=new QuestionsFragment();
        bundle=Bundle.EMPTY;
        //activity= Robolectric.setupActivity(MainActivity.class);
        activity= Robolectric.buildActivity(MainActivity.class).create().get();
        questionsFragment.onCreate(bundle);
        System.out.println("setup");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnCreate() throws Exception {
        verify(presenter).onCreate(questionsFragment,bundle);
    }

    @Test
    public void testOnCreateView() throws Exception {
        questionsFragment.onCreateView(LayoutInflater.from(activity),(ViewGroup)activity.findViewById(R.id.container),null);
        verify(presenter).onCreateView();
    }

    @Test
    public void testOnStop() throws Exception {
        questionsFragment.onStop();
        verify(presenter).onStop();
    }


}
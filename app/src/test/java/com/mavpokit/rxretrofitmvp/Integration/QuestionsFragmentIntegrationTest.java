package com.mavpokit.rxretrofitmvp.Integration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragment;
import com.mavpokit.rxretrofitmvp.View.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Alex on 07.09.2016.
 */
public class QuestionsFragmentIntegrationTest extends BaseIntegrationTest{

    QuestionsFragment questionsFragment;

    @Inject
    IQuestionsPresenter presenter;


    Bundle bundle;

    MainActivity activity;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        questionsFragment=spy(new QuestionsFragment());
        bundle=Bundle.EMPTY;
        activity= Robolectric.setupActivity(MainActivity.class);
        //activity= Robolectric.buildActivity(MainActivity.class).create().get();
        questionsFragment.onCreate(bundle);
        questionsFragment.onCreateView(LayoutInflater.from(activity),(ViewGroup)activity.findViewById(R.id.container),null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSearchOk() throws Exception {
        questionsFragment.search(Consts.QUERY);
        ListQuestion expectedListQuestion = presenter.getListQuestion();
        verify(questionsFragment).showQuestionList(expectedListQuestion);
    }

    @Test
    public void testSearchError() throws Exception {
        questionsFragment.search(Consts.QUERY_ERROR);
        verify(questionsFragment).showError(Consts.HTTP_404_CLIENT_ERROR);
    }


}

package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.DI.MyTestApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
/**
 * Created by Alex on 22.08.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = MyTestApplication.class,
        constants = BuildConfig.class, sdk = 19)
public class AnswersPresenterTest {

    @Inject
    IAnswersPresenter presenter;

    @Mock
    IAnswersView view;

    private static final String LINK="https://github.com";
    private static final Question question=new Question(LINK,"title",1,0);

    @Before
    public void setUp() throws Exception {
        MyTestApplication.getAppTestComponent().inject(this);
        MockitoAnnotations.initMocks(this);

        presenter.onCreate(view,question);

    }


    @Test
    public void testOnCreate() throws Exception {

    }

    @Test
    public void testOnCreateView() throws Exception {
        //when answer count == 0, show only question
        question.setAnswer_count(0);
        presenter.onCreate(view,question);
        presenter.onCreateView();
        verify(view).showQuestion(question);


    }

    @Test
    public void testOnSaveInstanceState() throws Exception {

    }

    @Test
    public void testOnStop() throws Exception {

    }

//    @Test
//    public void testOpenLink() throws Exception {
//        presenter.openLink();
//        verify(view).openLink(Uri.parse(question.getLink()));
//    }
}
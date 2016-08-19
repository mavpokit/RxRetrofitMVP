package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Alex on 18.08.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class QuestionsPresenterTest {

    private static final String LINK="https://github.com";
    Question question;
    ArrayList<Question> questionsList=new ArrayList<>();
    private ListQuestion listQuestion=new ListQuestion();

    @Mock
    private IQuestionsView view;

    //@Inject
    IQuestionsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        //MyApplication.getAppComponent().inject(this);
        MockitoAnnotations.initMocks(this);

        question=new Question(LINK,"title",1,0);
        questionsList.add(question);
        listQuestion.setItems(questionsList);

        presenter=new QuestionsPresenter();
        presenter.onCreate(view,null);
        presenter.setListQuestion(listQuestion);
    }

    @Test
    public void testShowAnswers() throws Exception {
        final int INDEX=0;
        presenter.showAnswers(INDEX);
        verify(view).openAnswers(listQuestion.getItems().get(INDEX));
    }

    @Test
    public void testopenLink() throws Exception {
        final int INDEX=0;
        presenter.openLink(INDEX);

        Uri uri=Uri.parse(LINK);
        verify(view).openLink(uri);
    }
}
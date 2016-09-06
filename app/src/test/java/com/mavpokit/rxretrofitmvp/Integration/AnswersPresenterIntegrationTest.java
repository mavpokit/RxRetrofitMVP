package com.mavpokit.rxretrofitmvp.Integration;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 06.09.2016.
 */
public class AnswersPresenterIntegrationTest extends BaseIntegrationTest{

    @Inject
    IAnswersPresenter presenter;

    @Mock
    IAnswersView view;

    private static Question question;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);
        question=new Question(Consts.LINK, "title", 1, Consts.QUESTION_ID);

    }


    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate(view,question);
        assertEquals(presenter.isNewQuestion(),true);
    }

    @Test
    public void testOnCreateView0answers() throws Exception {
        //when answer count == 0, show only question and text "No answers"
        question.setAnswer_count(0);
        presenter.onCreate(view,question);
        presenter.onCreateView();
        verify(view).showQuestion(question);
        verify(view).showNothing();
    }

    @Test
    public void testOnCreateViewAnswerOk() throws Exception {
        //when answer count > 0, show only question and load answers
        question.setAnswer_count(1);

        presenter.onCreate(view,question);
        presenter.onCreateView();

        verify(view).showQuestion(question);
        verify(view).showSpinner();
        verify(view).showAnswerList(any());
        verify(view).hideSpinner();
        verify(view,times(0)).showNothing();

        ListAnswer actualListAnswer = presenter.getListAnswer();
        for (int i = 0; i < actualListAnswer.getItems().size(); ++i) {
            Answer actual = actualListAnswer.getItems().get(i);
            Answer expected = mListAnswer.getItems().get(i);
            assertEquals(actual, expected);
        }



    }

    @Test
    public void testOnCreateViewRotate() throws Exception {
        presenter.onCreate(view,question);
        presenter.setNewQuestion(false);
        presenter.setListAnswer(mListAnswer);
        presenter.onCreateView();
        verify(view).showAnswerList(mListAnswer);
        verify(view,times(0)).showSpinner();
    }

    @Test
    public void onError() throws Exception{
        question.setQuestion_id(Consts.QUESTION_ID_WRONG);
        presenter.onCreate(view,question);
        presenter.onCreateView();
        verify(view).showSpinner();
        verify(view).showError(Consts.HTTP_404_CLIENT_ERROR);
        verify(view).hideSpinner();

    }

    @Test
    public void testOnSaveInstanceState() throws Exception {

    }

    @Test
    public void testOnStop() throws Exception {

    }

    @Test
    public void testOpenLink() throws Exception {
        presenter.onCreate(view,question);
        presenter.openLink();
        verify(view).openLink(Uri.parse(question.getLink()));
    }
}

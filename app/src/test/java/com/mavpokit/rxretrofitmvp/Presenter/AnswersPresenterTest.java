package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 * Created by Alex on 22.08.2016.
 */
public class AnswersPresenterTest extends BaseTest {

    @Inject
    IAnswersPresenter presenter;

    @Inject
    IModel model;

    @Mock
    IAnswersView view;

    private static final String LINK="https://github.com";
    private static final String ERROR_MESSAGE="ERROR_MESSAGE";
    private static final Question question=new Question(LINK,"title",1,2);
    private ArrayList<Answer> answerList=new ArrayList<>();
    private ListAnswer listAnswer=new ListAnswer();



    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);

        answerList.add(new Answer(1,true,"answer body"));
        listAnswer.setItems(answerList);

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
    public void testOnCreateView1answer() throws Exception {
        //when answer count > 0, show only question and load answers
        question.setAnswer_count(1);
        when( model.getAnswerList(String.valueOf(question.getQuestion_id())) )
                .thenReturn(Observable.just(listAnswer));
        presenter.onCreate(view,question);
        presenter.onCreateView();
        verify(view).showQuestion(question);
        verify(view).showSpinner();
        verify(view).showAnswerList(listAnswer);
        verify(view).hideSpinner();
        verify(view,times(0)).showNothing();
    }

    @Test
    public void testOnCreateViewRotate() throws Exception {
        presenter.onCreate(view,question);
        presenter.setNewQuestion(false);
        presenter.setListAnswer(listAnswer);
        presenter.onCreateView();
        verify(view).showAnswerList(listAnswer);
        verify(view,times(0)).showSpinner();
    }

    @Test
    public void onError() throws Exception{
        question.setAnswer_count(1);
        when(model.getAnswerList(String.valueOf(question.getQuestion_id())) )
                .thenReturn(Observable.error(new Throwable(ERROR_MESSAGE)));
        presenter.onCreate(view,question);
        presenter.onCreateView();
        verify(view).showSpinner();
        verify(view).showError(ERROR_MESSAGE);
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
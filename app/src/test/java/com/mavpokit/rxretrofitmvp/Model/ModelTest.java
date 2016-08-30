package com.mavpokit.rxretrofitmvp.Model;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 26.08.2016.
 */
@Ignore
public class ModelTest extends BaseTest {

    @Inject
    StackoverflowApiInterface api;

    IModel model = new Model();

    private static final String QUERY = "QUERY";
    private static final String LINK="https://github.com";

    Question question = new Question(LINK,"title",1,0);
    ArrayList<Question> questionsList=new ArrayList<>();
    private ListQuestion mListQuestion =new ListQuestion();

    private ArrayList<Answer> answerList=new ArrayList<>();
    private ListAnswer mListAnswer=new ListAnswer();


    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetQuestionList() throws Exception {
        questionsList.add(question);
        mListQuestion.setItems(questionsList);

        Observable<ListQuestion> expected = Observable.just(mListQuestion);
        when(api.getQuestions(QUERY)).thenReturn(expected);

        TestSubscriber<ListQuestion> testSubscriber = new TestSubscriber<>();
        model.getQuestionList(QUERY).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        ListQuestion actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(actual,mListQuestion);

    }

    @Test
    public void testGetAnswerList() throws Exception {
        answerList.add(new Answer(1,true,"answer body"));
        mListAnswer.setItems(answerList);

        Observable<ListAnswer> expected = Observable.just(mListAnswer);
        when(api.getAnswers(String.valueOf(question.getQuestion_id()))).thenReturn(expected);

        TestSubscriber<ListAnswer> testSubscriber = new TestSubscriber<>();
        model.getAnswerList(String.valueOf(question.getQuestion_id())).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        ListAnswer actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(actual,mListAnswer);

    }
}
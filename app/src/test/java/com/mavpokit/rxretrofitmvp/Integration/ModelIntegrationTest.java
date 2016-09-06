package com.mavpokit.rxretrofitmvp.Integration;

import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;
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
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 01.09.2016.
 */

public class ModelIntegrationTest extends BaseIntegrationTest {

    @Inject
    IModel model;

    private static final String LINK = "https://github.com";

    Question question = new Question(LINK, "title", 1, Consts.QUESTION_ID);

    private ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS_FILE);
    private ListAnswer mListAnswer = jsonReader.getListAnswer(Consts.JSONANSWERS_FILE);


    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
    }

    @Test
    public void testGetQuestionList() throws Exception {
        TestSubscriber<ListQuestion> testSubscriber = new TestSubscriber<>();
        model.getQuestionList(Consts.QUERY).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        ListQuestion actualListQuestion = testSubscriber.getOnNextEvents().get(0);

        for (int i = 0; i < actualListQuestion.getItems().size(); ++i) {
            Question actualQustion = actualListQuestion.getItems().get(i);
            Question expected = mListQuestion.getItems().get(i);
            assertEquals(actualQustion, expected);
        }


    }

    @Test
    public void testGetAnswerList() throws Exception {
        TestSubscriber<ListAnswer> testSubscriber = new TestSubscriber<>();
        model.getAnswerList(String.valueOf(question.getQuestion_id())).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        ListAnswer actualListAnswer = testSubscriber.getOnNextEvents().get(0);
        for (int i = 0; i < actualListAnswer.getItems().size(); ++i) {
            Answer actual = actualListAnswer.getItems().get(i);
            Answer expected = mListAnswer.getItems().get(i);
            assertEquals(actual, expected);
        }

    }
}

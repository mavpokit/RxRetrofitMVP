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
@Ignore
public class ApiModelIntegrationTest extends BaseIntegrationTest{

    @Inject
    IModel model;
    //IModel model = new Model();

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
    }

    @Test
    public void testGetQuestionList() throws Exception {
        TestSubscriber<ListQuestion> testSubscriber = new TestSubscriber<>();
        model.getQuestionList(QUERY).subscribe(testSubscriber);

//        testSubscriber.assertNoErrors();
//        testSubscriber.assertValueCount(1);
//
//        ListQuestion actual = testSubscriber.getOnNextEvents().get(0);
//        assertEquals(actual,mListQuestion);

    }

    @Ignore @Test
    public void testGetAnswerList() throws Exception {
        TestSubscriber<ListAnswer> testSubscriber = new TestSubscriber<>();
        model.getAnswerList(String.valueOf(question.getQuestion_id())).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        ListAnswer actual = testSubscriber.getOnNextEvents().get(0);
        assertEquals(actual,mListAnswer);

    }
}

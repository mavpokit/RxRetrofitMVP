package com.mavpokit.rxretrofitmvp.Model.Api;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.JsonReader;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterfaceTest;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import okhttp3.mockwebserver.RecordedRequest;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;

@Ignore
public class StackoverflowApiInterfaceTest
        extends BaseTest {
    MockWebServer server;
    StackoverflowApiInterface apiInterface;
    private static final String QUERY = "java";
    private static final String QUESTION_ID = "23804123";
    ListQuestion mListQuestion;
    ListAnswer mListAnswer;
    JsonReader jsonReader = new JsonReader();
    private static final String JSONQUESTIONS = "getquestions.json";
    private static final String JSONANSWERS = "getanswers.json";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        String getQuestionsResponce = this.jsonReader.read("getquestions.json");
        mListQuestion = this.jsonReader.getListQuestion("getquestions.json");
        String getAnswersResponce = this.jsonReader.read("getanswers.json");
        mListAnswer = this.jsonReader.getListAnswer("getanswers.json");

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                System.out.println(request.getPath());//for debug
                if (request.getPath().equals("/2.2/search?order=desc&sort=activity&site=stackoverflow&filter=withbody&tagged="+QUERY)) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getQuestionsResponce);
                }
                if (request.getPath().equals("/2.2/questions/"+QUESTION_ID+"/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getAnswersResponce);
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher((Dispatcher)dispatcher);
        HttpUrl baseUrl = this.server.url("/");
        apiInterface = ApiModule.getApiInterface((String)baseUrl.toString());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetQuestions() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        apiInterface.getQuestions("java").subscribe((Subscriber)testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        ListQuestion actualListQuestion = (ListQuestion)testSubscriber.getOnNextEvents().get(0);
        for (int i = 0; i < actualListQuestion.getItems().size(); ++i) {
            Question actual = actualListQuestion.getItems().get(i);
            Question expected = mListQuestion.getItems().get(i);
            assertEquals(actual, expected);
        }
    }

    @Test
    public void testGetAnswers() throws Exception {
        TestSubscriber testSubscriber = new TestSubscriber();
        apiInterface.getAnswers("23804123").subscribe((Subscriber)testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
        ListAnswer actualListAnswer = (ListAnswer)testSubscriber.getOnNextEvents().get(0);
        for (int i = 0; i < actualListAnswer.getItems().size(); ++i) {
            Answer actual = actualListAnswer.getItems().get(i);
            Answer expected = mListAnswer.getItems().get(i);
            assertEquals(actual, expected);
        }
    }
}
package com.mavpokit.rxretrofitmvp.Integration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;
import com.mavpokit.rxretrofitmvp.R;
import com.mavpokit.rxretrofitmvp.View.Fragments.AnswersFragment;
import com.mavpokit.rxretrofitmvp.View.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Alex on 07.09.2016.
 */
public class AnswersFragmentIntegrationTest extends BaseIntegrationTest{
    @Inject
    IAnswersPresenter presenter;

    @Inject
    MockWebServer server;

    AnswersFragment answersFragment;

    Bundle bundle;

    MainActivity activity;

    Question question = new Question(Consts.LINK, "title", 1, Consts.QUESTION_ID);

    String getAnswersResponce = jsonReader.read(Consts.JSONANSWERS_FILE);

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        answersFragment=spy(AnswersFragment.getInstance(question));
        bundle=Bundle.EMPTY;
        activity= Robolectric.setupActivity(MainActivity.class);
        //activity= Robolectric.buildActivity(MainActivity.class).create().get();
        answersFragment.onCreate(bundle);
        System.out.println("setup");
        question.setBody("");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testShowAnswersOk() throws Exception {
        setDispatcherResultOk();
        answersFragment.onCreateView(LayoutInflater.from(activity),(ViewGroup)activity.findViewById(R.id.container),null);
        ListAnswer expectedListAnswer = presenter.getListAnswer();
        verify(answersFragment).showAnswerList(expectedListAnswer);
    }

    @Test
    public void testShowAnswersError() throws Exception {
        setDispatcherError();
        answersFragment.onCreateView(LayoutInflater.from(activity),(ViewGroup)activity.findViewById(R.id.container),null);
        verify(answersFragment).showError(Consts.HTTP_404_CLIENT_ERROR);
    }

    void setDispatcherResultOk() {
        server.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().equals("/2.2/questions/"+ Consts.QUESTION_ID+"/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getAnswersResponce);
                }

                return new MockResponse().setResponseCode(404);

            }
        });
    }

    private void setDispatcherError() {
        server.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                return new MockResponse().setResponseCode(404);
            }
        });

    }



}

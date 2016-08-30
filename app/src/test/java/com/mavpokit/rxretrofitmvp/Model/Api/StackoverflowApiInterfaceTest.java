package com.mavpokit.rxretrofitmvp.Model.Api;

import com.mavpokit.rxretrofitmvp.BaseTest;
import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.DI.MyTestApplication;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

//import com.squareup.okhttp.HttpUrl;
//import com.squareup.okhttp.mockwebserver.Dispatcher;
//import com.squareup.okhttp.mockwebserver.MockResponse;
//import com.squareup.okhttp.mockwebserver.MockWebServer;
//import com.squareup.okhttp.mockwebserver.RecordedRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by Alex on 27.08.2016.
 */
@Ignore
public class StackoverflowApiInterfaceTest extends BaseTest {
    //private final static String BASEURL="api.com/";
    MockWebServer server;
    StackoverflowApiInterface apiInterface;

    @Before
    public void setUp() throws Exception {
        super.setUp();

    }


    @Test
    public void testGetQuestions() throws Exception {
        server = new MockWebServer();
        server.start();

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                System.out.println("***************************************");
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);

        HttpUrl baseUrl = server.url("/");
        apiInterface = ApiModule.getApiInterface(baseUrl.toString());

        TestSubscriber<ListQuestion> subscriber=new TestSubscriber<>();
        apiInterface.getQuestions("java").subscribe(subscriber);

        //call();


        //Thread.sleep(1);


    }



    @After
    public void tearDown() throws Exception {
        server.shutdown();

    }


//    @Test
//    public void testGetAnswers() throws Exception {
//
//    }
}
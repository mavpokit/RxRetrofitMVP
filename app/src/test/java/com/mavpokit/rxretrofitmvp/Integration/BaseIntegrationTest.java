package com.mavpokit.rxretrofitmvp.Integration;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.Integration.DI.AppIntegrationTestComponent;
import com.mavpokit.rxretrofitmvp.Integration.DI.MyIntegrationTestApplication;
import com.mavpokit.rxretrofitmvp.Model.Api.JsonReader;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by Alex on 01.09.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = MyIntegrationTestApplication.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public class BaseIntegrationTest {

    AppIntegrationTestComponent component;
    protected JsonReader jsonReader = new JsonReader();
    ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS_FILE);
    ListAnswer mListAnswer = jsonReader.getListAnswer(Consts.JSONANSWERS_FILE);


    @Inject
    MockWebServer server;


    @Before
    public void setUp() throws Exception {
        component = (AppIntegrationTestComponent) MyIntegrationTestApplication.getAppComponent();
    }

}

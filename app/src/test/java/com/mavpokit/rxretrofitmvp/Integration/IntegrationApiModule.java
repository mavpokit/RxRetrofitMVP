package com.mavpokit.rxretrofitmvp.Integration;

import com.mavpokit.rxretrofitmvp.Integration.DI.AppIntegrationTestComponent;
import com.mavpokit.rxretrofitmvp.Integration.DI.MyIntegrationTestApplication;
import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;

import org.junit.Ignore;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;


/**
 * Created by Alex on 27.07.2016.
 */
@Ignore
public class IntegrationApiModule extends BaseIntegrationTest {

    public IntegrationApiModule() {
        ((AppIntegrationTestComponent) MyIntegrationTestApplication.getAppComponent()).inject(this);
    }

    public StackoverflowApiInterface getApiInterface()throws IOException{
//    public StackoverflowApiInterface getApiInterface(MockWebServer server)throws IOException{

        server.start();
        String getQuestionsResponce = jsonReader.read(Consts.JSONQUESTIONS_FILE);
        String getAnswersResponce = jsonReader.read(Consts.JSONANSWERS_FILE);

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                System.out.println(request.getPath());//for debug
                if (request.getPath().equals("/2.2/search?order=desc&sort=activity&site=stackoverflow&filter=withbody&tagged="+Consts.QUERY)) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getQuestionsResponce);
                }
                if (request.getPath().equals("/2.2/search?order=desc&sort=activity&site=stackoverflow&filter=withbody&tagged="+Consts.QUERY_NULL)) {
                    return new MockResponse().setResponseCode(200)
                            .setBody("{}");
                }
                if (request.getPath().equals("/2.2/questions/"+Consts.QUESTION_ID+"/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getAnswersResponce);
                }

                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher((Dispatcher)dispatcher);
        HttpUrl baseUrl = server.url(Consts.BASEURL);

        return ApiModule.getApiInterface((String)baseUrl.toString());
    }

}

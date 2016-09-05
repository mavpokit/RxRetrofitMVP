package com.mavpokit.rxretrofitmvp.Integration;

//import okhttp3.HttpUrl;
import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.JsonReader;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Const;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import java.io.IOError;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit.GsonConverterFactory;
//import retrofit.Retrofit;
//import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by Alex on 27.07.2016.
 */
public class IntegrationApiModule extends BaseIntegrationTest {
    public static StackoverflowApiInterface getApiInterface(MockWebServer server)throws IOException{

        server.start();
        JsonReader jsonReader=new JsonReader();
        String getQuestionsResponce = jsonReader.read(Consts.JSONQUESTIONS);
        ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS);
        String getAnswersResponce = jsonReader.read(Consts.JSONANSWERS);
        ListAnswer mListAnswer = jsonReader.getListAnswer(Consts.JSONANSWERS);

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                System.out.println(request.getPath());//for debug
                if (request.getPath().equals("/2.2/search?order=desc&sort=activity&site=stackoverflow&filter=withbody&tagged="+Consts.QUERY)) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(getQuestionsResponce);
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

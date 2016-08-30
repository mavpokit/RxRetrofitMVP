package com.mavpokit.rxretrofitmvp.Model.Api;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
//import retrofit.http.GET;
//import retrofit.http.Path;
//import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Alex on 27.07.2016.
 */
public interface StackoverflowApiInterface {
    @GET("2.2/search?order=desc&sort=activity&site=stackoverflow&filter=withbody")
    Observable<ListQuestion> getQuestions(@Query("tagged") String searchText);

    @GET("/2.2/questions/{ids}/answers?order=desc&sort=activity&site=stackoverflow&filter=withbody")
    Observable<ListAnswer> getAnswers(@Path("ids") String questionId);
}

package com.mavpokit.rxretrofitmvp.View;

import com.mavpokit.rxretrofitmvp.DI.Consts;
import com.mavpokit.rxretrofitmvp.DI.JsonReader;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.when;

/**
 * Created by Alex on 08.09.2016.
 */

public class ApiConfig {
    @Inject
    StackoverflowApiInterface apiInterface;

    protected JsonReader jsonReader = new JsonReader();
    ListQuestion mListQuestion = jsonReader.getListQuestion(Consts.JSONQUESTIONS_FILE);
    ListAnswer mListAnswer = jsonReader.getListAnswer(Consts.JSONANSWERS_FILE);


    @Inject
    public ApiConfig() {
    }

    void setListQuestionsResultOk(){
        Observable<ListQuestion> expected = Observable.just(mListQuestion);
        when(apiInterface.getQuestions(Consts.QUERY)).thenReturn(expected);
//        Observable<ListAnswer> expectedAQ = Observable.just(mListAnswer);
//        when(apiInterface.getAnswers(Consts.QUESTION_ID)).thenReturn(expectedAQ);
    }

    void setListQuestionsResultEmpty(){
        Observable<ListQuestion> expected = Observable.just(null);
        when(apiInterface.getQuestions(Consts.QUERY)).thenReturn(expected);
    }

    void setListQuestionsResultError(){
        when(apiInterface.getQuestions(Consts.QUERY)).thenReturn(Observable.error(new Throwable(Consts.HTTP_404_CLIENT_ERROR)));
    }

    void setListAnswersResultOk(){
        Observable<ListAnswer> expected = Observable.just(mListAnswer);
        when(apiInterface.getAnswers(Consts.QUESTION_ID)).thenReturn(expected);
    }

    void setListAnswersResultEmpty(){
        Observable<ListAnswer> expected = Observable.just(null);
        when(apiInterface.getAnswers(Consts.QUESTION_ID)).thenReturn(expected);
    }

    void setListAnswersResultError(){
        when(apiInterface.getAnswers(Consts.QUESTION_ID)).thenReturn(Observable.error(new Throwable(Consts.HTTP_404_CLIENT_ERROR)));
    }



}

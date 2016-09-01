package com.mavpokit.rxretrofitmvp.Model;

import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

/**
 * Created by Alex on 27.07.2016.
 */
public class Model implements IModel {
    @Inject
    StackoverflowApiInterface apiInterface;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler ioScheduler;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler uiScheduler;

    public Model() {
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    public Observable<ListQuestion> getQuestionList(String query) {
        //return ApiModule.getApiInterface().getQuestions(query) //before DI
        return apiInterface.getQuestions(query)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @Override
    public Observable<ListAnswer> getAnswerList(String questionId) {
        //return ApiModule.getApiInterface().getAnswers(questionId) //before DI
        return apiInterface.getAnswers(questionId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }
}

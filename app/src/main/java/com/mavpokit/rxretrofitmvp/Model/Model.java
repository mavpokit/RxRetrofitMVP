package com.mavpokit.rxretrofitmvp.Model;

import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Alex on 27.07.2016.
 */
public class Model implements IModel {
    @Override
    public Observable<ListQuestion> getQuestionList(String query) {
        return ApiModule.getApiInterface().getQuestions(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ListAnswer> getAnswerList(String questionId) {
        return ApiModule.getApiInterface().getAnswers(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

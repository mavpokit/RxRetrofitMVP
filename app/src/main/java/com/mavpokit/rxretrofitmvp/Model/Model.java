package com.mavpokit.rxretrofitmvp.Model;

import com.google.gson.Gson;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Util.JsonReader;
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

    String[] suggestions = new String[]{};

    public Model() {
        //if (MyApplication.getAppComponent()==null) System.out.println("null");
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

    @Override
    public Observable<String[]> loadSuggestions() {

//        final String[] SUGGESTIONS = {
//                "java", "android", "activity",
//                "fragment", "service", "content provider",
//                "lidecycle", "retrofit"
//        };
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        suggestions = SUGGESTIONS;

        String s=new JsonReader().read("suggestions.json");
        Gson gson = new Gson();
        suggestions=gson.fromJson(s,String[].class);

        Observable<String[]> suggestionsObservable = Observable.just(suggestions);
        return suggestionsObservable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);

    }

    @Override
    public String getSuggestion(int position) {
        return suggestions[position];
    }
}

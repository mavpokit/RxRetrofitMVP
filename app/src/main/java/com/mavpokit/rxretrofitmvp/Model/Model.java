package com.mavpokit.rxretrofitmvp.Model;

import com.google.gson.Gson;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.Realm.RealmString;
import com.mavpokit.rxretrofitmvp.Util.JsonReader;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;
import io.realm.RealmResults;
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

//it's bad idea to inject realm because we don't know when to close it
//    @Inject Realm realm;

    List<String> suggestions = new ArrayList<>();

    public Model() {
        //if (MyApplication.getAppComponent()==null) System.out.println("null");
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    public Observable<ListQuestion> getQuestionList(String query) {
        //return ApiModule.getApiInterface().getQuestions(query) //before DI
        return apiInterface.getQuestions(query)
                .observeOn(uiScheduler)
                .subscribeOn(ioScheduler);

    }

    @Override
    public Observable<ListAnswer> getAnswerList(String questionId) {
        //return ApiModule.getApiInterface().getAnswers(questionId) //before DI
        return apiInterface.getAnswers(questionId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @Override
    public Observable<List<String>> loadSuggestions() {
        Realm realm = Realm.getDefaultInstance();
        Observable<List<String>> suggestionsObservable = realm.where(RealmString.class).findAllAsync().asObservable()
                .filter(realmStrings -> realmStrings.isLoaded())
                .flatMap(realmStrings -> {
                    List<String> list = new ArrayList<String>();
                    for (int i = realmStrings.size()-1; i >= 0 ; i--)
                        list.add(realmStrings.get(i).getValue());
                    return Observable.just(list);
                    });
        realm.close();
        return suggestionsObservable;

//----------------------load from json file in resources------------------
//        String s=new JsonReader().read("suggestions.json");
//        Gson gson = new Gson();
//        suggestions=gson.fromJson(s,ArrayList.class);
//
//        Observable<List<String>> suggestionsObservable = Observable.just(suggestions);
//        return suggestionsObservable
//                .subscribeOn(ioScheduler)
//                .observeOn(uiScheduler);

//        final String[] SUGGESTIONS = {"java", "android", "activity"};
//        suggestions = SUGGESTIONS;


    }

    @Override
    public String getSuggestion(int position) {
        return suggestions.get(position);
    }

    @Override
    public void addQueryToSuggestionsList(String query, RealmAddListener listener) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(new RealmString(query));
        realm.commitTransaction();
        listener.onAdd();

        realm.close();

    }
}

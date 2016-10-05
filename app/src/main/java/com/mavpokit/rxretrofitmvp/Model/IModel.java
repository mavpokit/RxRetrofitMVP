package com.mavpokit.rxretrofitmvp.Model;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

import java.util.List;

import rx.Observable;

/**
 * Created by Alex on 27.07.2016.
 */
public interface IModel {
    Observable<ListQuestion> getQuestionList(String query);
    Observable<ListAnswer> getAnswerList(String questionId);
    public Observable<List<String>> loadSuggestions();
    String getSuggestion(int position);
    void addQueryToSuggestionsList(String query, RealmAddListener listener);

    interface RealmAddListener{
        void onAdd();
    }
}

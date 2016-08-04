package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alex on 27.07.2016.
 */
public class QuestionsPresenter implements IQuestionsPresenter {

    IModel model = new Model();
    private IQuestionsView view;
    private Subscription subscription = Subscriptions.empty();
    private ListQuestion listQuestion;
    private final static String Q_LIST_KEY = "questionList";

    public QuestionsPresenter(IQuestionsView view) {
        this.view = view;
    }

    @Override
    public void onSearchClick(String query) {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();

        view.showSpinner();

        subscription = model.getQuestionList(query).subscribe(new Observer<ListQuestion>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(ListQuestion questionList) {
                if (isListEmpty(questionList)) {
                    listQuestion = questionList;
                    view.showQuestionList(questionList);
                    view.hideSpinner();
                } else
                    view.showNoAnswer();
            }
        });

    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listQuestion = (ListQuestion) savedInstanceState.getSerializable(Q_LIST_KEY);
            if (isListEmpty(listQuestion)) {
                view.showQuestionList(listQuestion);
            }

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Q_LIST_KEY, listQuestion);
    }

    private boolean isListEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

}

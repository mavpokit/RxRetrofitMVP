package com.mavpokit.rxretrofitmvp.Presenter;

import android.content.Intent;
import android.net.Uri;
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
                if (isListNotEmpty(questionList)) {
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
        if (savedInstanceState != null)
            listQuestion = (ListQuestion) savedInstanceState.getSerializable(Q_LIST_KEY);

        if (isListNotEmpty(listQuestion))
                view.showQuestionList(listQuestion);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (isListNotEmpty(listQuestion))
        outState.putSerializable(Q_LIST_KEY, listQuestion);
    }

    @Override
    public void showAnswers(int position) {
        view.openAnswers(listQuestion.getItems().get(position));
    }

    @Override
    public void openLink(int position) {
        view.openLink(Uri.parse(listQuestion.getItems().get(position).getLink()));
    }

    private boolean isListNotEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

}

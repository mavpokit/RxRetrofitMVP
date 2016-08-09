package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;
import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alex on 05.08.2016.
 */
public class AnswersPresenter implements IAnswersPresenter {
    private static final String A_LIST_KEY = "A_LIST_KEY";
    IAnswersView view;
    IModel model = new Model();
    private ListAnswer listAnswer;
    private Question question;//we het it from args, so it is retained
    private Subscription subscription = Subscriptions.empty();


    public AnswersPresenter(IAnswersView view, Question question) {
        this.view = view;
        this.question = question;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            listAnswer = (ListAnswer) savedInstanceState.getSerializable(A_LIST_KEY);
    }

    private void loadAnswers() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();

        view.showSpinner();

        subscription = model.getAnswerList(String.valueOf(question.getQuestion_id())).subscribe(new Observer<ListAnswer>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.showError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(ListAnswer answerList) {
                if (isListNotEmpty(answerList)) {
                    listAnswer = answerList;
                    view.showAnswerList(listAnswer);
                    view.hideSpinner();
                } else
                    view.showNothing();
            }
        });

    }

    private boolean isListNotEmpty(ListAnswer answerList) {
        return answerList != null && !answerList.getItems().isEmpty();
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        view.showQuestion(question);
        if (isListNotEmpty(listAnswer))
            view.showAnswerList(listAnswer);
        else
            loadAnswers();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(A_LIST_KEY, listAnswer);
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
    }

    @Override
    public void openLink() {
        view.openLink(Uri.parse(question.getLink()));
    }


}

package com.mavpokit.rxretrofitmvp.Presenter;

import android.net.Uri;
import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.DI.MyApplication;
import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Alex on 27.07.2016.
 */
public class QuestionsPresenter implements IQuestionsPresenter {

    //IModel model = new Model(); //before DI
    @Inject
    IModel model;

    private IQuestionsView view;
    private Subscription subscription = Subscriptions.empty();
    private Subscription subscriptionSuggestions = Subscriptions.empty();
    private ListQuestion listQuestion;
    private final static String Q_LIST_KEY = "questionList";

    public QuestionsPresenter()
    {
        MyApplication.getAppComponent().inject(this);
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
                view.hideSpinner();
            }

            @Override
            public void onNext(ListQuestion questionList) {
                if (isListNotEmpty(questionList)) {
                    listQuestion = questionList;
                    view.showQuestionList(questionList);
                    view.hideSpinner();
                }
                else
                {
                    view.showNothing();
                    view.hideSpinner();
                }
            }
        });

        model.addQueryToSuggestionsList(query, () -> initSuggestionList());

    }



    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed())
            subscription.unsubscribe();
        if (!subscriptionSuggestions.isUnsubscribed())
            subscriptionSuggestions.unsubscribe();
    }

    @Override
    public void onDestroy() {
        listQuestion=null;
    }

    @Override
    public void onCreate(IQuestionsView view, Bundle savedInstanceState) {
        this.view = view;
//        if (savedInstanceState != null) listQuestion = (ListQuestion) savedInstanceState.getSerializable(Q_LIST_KEY);
        initSuggestionList();

    }


    /**
     * model asynchronously loads suggestions array and view adds it to searchview suggestion list
     */
    void initSuggestionList() {

        if (!subscriptionSuggestions.isUnsubscribed())
            subscriptionSuggestions.unsubscribe();

        subscriptionSuggestions=model.loadSuggestions().subscribe(new Observer<List<String>>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {}

            @Override
            public void onNext(List<String> strings) {
                System.out.println("----------------------------------"+strings);
                view.initSuggestions(strings);
            }
        });
        //this case worked well, but in QuestionsFragmentIntegrationTest we got rx.exceptions.OnErrorNotImplementedException
        //subscriptionSuggestions=model.loadSuggestions().subscribe(strings -> view.initSuggestions(strings));
    }

    @Override
    public void onCreateView() {
        if (isListNotEmpty(listQuestion))
            view.showQuestionList(listQuestion);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //if (isListNotEmpty(listQuestion))
        //outState.putSerializable(Q_LIST_KEY, listQuestion);
    }

    @Override
    public void showAnswers(int position) {
        view.openAnswers(listQuestion.getItems().get(position));
    }

    @Override
    public void openLink(int position) {
        view.openLink(Uri.parse(listQuestion.getItems().get(position).getLink()));
    }

    @Override
    public void onSuggestionClick(int position) {
        //suggestions are already loaded, so we can get suggest synchronously
        view.selectSuggestion(model.getSuggestion(position));
    }

    private boolean isListNotEmpty(ListQuestion questionList) {
        return questionList != null && !questionList.getItems().isEmpty();
    }

    //for Tests
    @Override
    public void setListQuestion(ListQuestion listQuestion) {
        this.listQuestion = listQuestion;
    }
    //for Tests
    @Override
    public ListQuestion getListQuestion() {
        return listQuestion;
    }
}

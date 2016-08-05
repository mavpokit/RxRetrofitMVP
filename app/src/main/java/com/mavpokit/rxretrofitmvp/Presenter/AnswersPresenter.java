package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;

import java.io.Serializable;

/**
 * Created by Alex on 05.08.2016.
 */
public class AnswersPresenter implements IAnswersPresenter {
    private static final String A_LIST_KEY = "A_LIST_KEY";
    IAnswersView view;
    private ListAnswer listAnswer;
    private Question question;


    public AnswersPresenter(IAnswersView view, Question question) {
        this.view = view;
        this.question = question;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            listAnswer = (ListAnswer) savedInstanceState.getSerializable(A_LIST_KEY);
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        if (isListEmpty(listAnswer))
            view.showAnswerList(listAnswer);
        view.showQuestion(question);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(A_LIST_KEY, listAnswer);
    }

    private boolean isListEmpty(ListAnswer answerList) {
        return answerList != null && !answerList.getItems().isEmpty();
    }
}

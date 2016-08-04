package com.mavpokit.rxretrofitmvp.View;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;

/**
 * Created by Alex on 27.07.2016.
 */
public interface IQuestionsView {
    void showQuestionList(ListQuestion questionList);
    void showNoAnswer();
    void showError(String errorMessage);
    void showSpinner();
    void hideSpinner();
}

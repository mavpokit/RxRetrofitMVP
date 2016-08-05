package com.mavpokit.rxretrofitmvp.View;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;

/**
 * Created by Alex on 04.08.2016.
 */
public interface IAnswersView {
    void showAnswerList(ListAnswer answerList);
    void showError(String errorMessage);
    void showSpinner();
    void hideSpinner();
}

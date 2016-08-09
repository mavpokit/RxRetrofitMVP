package com.mavpokit.rxretrofitmvp.View;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;

/**
 * Created by Alex on 04.08.2016.
 */
public interface IAnswersView {
    void showAnswerList(ListAnswer answerList);
    void showQuestion(Question question);
    void showError(String errorMessage);
    void showSpinner();
    void hideSpinner();
    void showNothing();
}

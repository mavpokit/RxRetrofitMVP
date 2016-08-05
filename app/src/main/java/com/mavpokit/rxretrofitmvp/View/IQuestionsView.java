package com.mavpokit.rxretrofitmvp.View;

import android.net.Uri;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;


/**
 * Created by Alex on 27.07.2016.
 */
public interface IQuestionsView {
    void showQuestionList(ListQuestion questionList);
    void showNoAnswer();
    void showError(String errorMessage);
    void showSpinner();
    void hideSpinner();
    void openLink(Uri link);
    void openAnswers(Question question);
}

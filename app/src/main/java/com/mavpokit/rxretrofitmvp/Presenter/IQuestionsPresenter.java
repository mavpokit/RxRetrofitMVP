package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListQuestion;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

/**
 * Created by Alex on 27.07.2016.
 */
public interface IQuestionsPresenter {
    void onSearchClick(String query);
    void onStop();
    void onCreate(IQuestionsView view, Bundle savedInstanceState);
    void onCreateView();
    void onSaveInstanceState(Bundle outState);
    void showAnswers(int position);
    void openLink(int position);

    //for Tests
    void setListQuestion(ListQuestion listQuestion);

    //for Tests
    ListQuestion getListQuestion();
}

package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

/**
 * Created by Alex on 04.08.2016.
 */
public interface IAnswersPresenter {
    public void onCreate(IAnswersView view, Question question/*, Bundle savedInstanceState*/);
    public void onCreateView(/*Bundle savedInstanceState*/);
    //void onSaveInstanceState(Bundle outState);
    void onStop();
    public void openLink();
}

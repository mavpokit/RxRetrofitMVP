package com.mavpokit.rxretrofitmvp.Presenter;

import android.os.Bundle;

import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Question;
import com.mavpokit.rxretrofitmvp.View.IAnswersView;
import com.mavpokit.rxretrofitmvp.View.IQuestionsView;

/**
 * Created by Alex on 04.08.2016.
 */
public interface IAnswersPresenter {
    public void onCreate(IAnswersView view, Question question/*, Bundle savedInstanceState*/);
    public void onCreateView(/*Bundle savedInstanceState*/);
    public void onStop();
    public void openLink();

    //for tests
    public void setNewQuestion(boolean newQuestion);
    public boolean isNewQuestion();
    public void setListAnswer(ListAnswer listAnswer);
}

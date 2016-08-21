package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Model;
import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenterTest;
import com.mavpokit.rxretrofitmvp.View.Fragments.AnswersFragment;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 12.08.2016.
 */
@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, QuestionsFragmentModule.class, AnswersFragmentModule.class})
public interface AppTestComponent extends AppComponent{
    public void inject(QuestionsPresenterTest questionsPresenterTest);
}

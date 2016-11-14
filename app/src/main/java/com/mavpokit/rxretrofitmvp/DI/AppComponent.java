package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Model;
import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;
import com.mavpokit.rxretrofitmvp.View.Fragments.AnswersFragment;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 12.08.2016.
 */
@Singleton
@Component(modules = {ApiProvider.class,
        SchedulerProvider.class,
        ModelProvider.class,
        QuestionsPresenterProvider.class,
        AnswersPresenterProvider.class,
//        RealmProvider.class
})
public interface AppComponent {
    public void inject(Model model);
    public void inject(QuestionsPresenter questionsPresenter);
    public void inject(QuestionsFragment questionsFragment);
    public void inject(AnswersPresenter answersPresenter);
    public void inject(AnswersFragment answersFragment);
}

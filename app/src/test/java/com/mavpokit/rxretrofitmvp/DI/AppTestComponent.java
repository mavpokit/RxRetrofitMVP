package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterfaceTest;
import com.mavpokit.rxretrofitmvp.Model.ModelTest;
import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenterTest;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenterTest;
import com.mavpokit.rxretrofitmvp.View.Fragments.AnswersFragmentTest;
import com.mavpokit.rxretrofitmvp.View.Fragments.QuestionsFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 12.08.2016.
 */
@Singleton
@Component(modules = {ApiMockProvider.class,
        ModelMockProvider.class,
        SchedulerMockProvider.class,
        QuestionsPresenterMockProvider.class,
        AnswersPresenterMockProvider.class})

public interface AppTestComponent extends AppComponent{
    public void inject(QuestionsPresenterTest questionsPresenterTest);
    public void inject(AnswersPresenterTest answersPresenterTest);
    public void inject(ModelTest modelTest);
    public void inject(QuestionsFragmentTest questionsFragmentTest);
    public void inject(AnswersFragmentTest answersFragmentTest);
    public void inject(StackoverflowApiInterfaceTest stackoverflowApiInterfaceTest);
}

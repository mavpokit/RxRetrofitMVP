package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.View.AnswersFragmentUiTest;
import com.mavpokit.rxretrofitmvp.View.ApiConfig;
import com.mavpokit.rxretrofitmvp.View.QuestionsFragmentUiTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 12.08.2016.
 */
@Singleton
@Component(modules = {
        ApiAndroidMockProvider.class,
        //ApiProvider.class,
        SchedulerProvider.class,
        ModelProvider.class,
        QuestionsPresenterProvider.class,
        AnswersPresenterProvider.class
})

public interface UiTestComponent extends AppComponent{
    void inject(ApiConfig apiConfig);
    void inject(QuestionsFragmentUiTest questionsFragmentUiTest);
    void inject(AnswersFragmentUiTest answersFragmentUiTest);
}

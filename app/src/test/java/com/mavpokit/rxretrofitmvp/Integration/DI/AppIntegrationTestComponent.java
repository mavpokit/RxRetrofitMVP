package com.mavpokit.rxretrofitmvp.Integration.DI;

import com.mavpokit.rxretrofitmvp.DI.AnswersPresenterProvider;
import com.mavpokit.rxretrofitmvp.DI.AppComponent;
import com.mavpokit.rxretrofitmvp.DI.ModelProvider;
import com.mavpokit.rxretrofitmvp.DI.QuestionsPresenterProvider;
import com.mavpokit.rxretrofitmvp.DI.SchedulerMockProvider;
import com.mavpokit.rxretrofitmvp.Integration.AnswersFragmentIntegrationTest;
import com.mavpokit.rxretrofitmvp.Integration.AnswersPresenterIntegrationTest;
import com.mavpokit.rxretrofitmvp.Integration.IntegrationApiModule;
import com.mavpokit.rxretrofitmvp.Integration.ModelIntegrationTest;
import com.mavpokit.rxretrofitmvp.Integration.QuestionsFragmentIntegrationTest;
import com.mavpokit.rxretrofitmvp.Integration.QuestionsPresenterIntegrationTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alex on 12.08.2016.
 */
@Singleton
@Component(modules = {
        IntegrationApiProvider.class,
        SchedulerMockProvider.class,
        ModelProvider.class,
        QuestionsPresenterProvider.class,
        AnswersPresenterProvider.class
})

public interface AppIntegrationTestComponent extends AppComponent{
    public void inject(ModelIntegrationTest modelIntegrationTest);
    public void inject(QuestionsPresenterIntegrationTest presenterIntegrationTest);
    public void inject(AnswersPresenterIntegrationTest answersPresenterIntegrationTest);
    public void inject(QuestionsFragmentIntegrationTest questionsFragmentIntegrationTest);
    public void inject(AnswersFragmentIntegrationTest answersFragmentIntegrationTest);
    public void inject(IntegrationApiModule integrationApiModule);
}

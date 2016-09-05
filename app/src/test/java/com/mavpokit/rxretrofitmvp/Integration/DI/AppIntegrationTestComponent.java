package com.mavpokit.rxretrofitmvp.Integration.DI;

import com.mavpokit.rxretrofitmvp.DI.AnswersPresenterProvider;
import com.mavpokit.rxretrofitmvp.DI.ApiProvider;
import com.mavpokit.rxretrofitmvp.DI.AppComponent;
import com.mavpokit.rxretrofitmvp.DI.ModelProvider;
import com.mavpokit.rxretrofitmvp.DI.QuestionsPresenterProvider;
import com.mavpokit.rxretrofitmvp.DI.SchedulerMockProvider;
import com.mavpokit.rxretrofitmvp.DI.SchedulerProvider;
import com.mavpokit.rxretrofitmvp.Integration.ApiModelIntegrationTest;

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
    public void inject(ApiModelIntegrationTest modelIntegrationTest);
}

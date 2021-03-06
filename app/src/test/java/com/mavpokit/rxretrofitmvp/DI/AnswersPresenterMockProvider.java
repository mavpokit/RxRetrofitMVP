package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Presenter.AnswersPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.IAnswersPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import static org.mockito.Mockito.mock;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class AnswersPresenterMockProvider {
    @Provides
    @Singleton
    IAnswersPresenter provideIAnswersPresenter(){
        return mock(AnswersPresenter.class);
    }
}

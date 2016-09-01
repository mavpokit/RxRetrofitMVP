package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Presenter.IQuestionsPresenter;
import com.mavpokit.rxretrofitmvp.Presenter.QuestionsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class QuestionsPresenterProvider {
    @Provides
    @Singleton
    IQuestionsPresenter provideQuestionsPresenter(){
        return new QuestionsPresenter();
    }
}

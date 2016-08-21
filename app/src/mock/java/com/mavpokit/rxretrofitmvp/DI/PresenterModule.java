package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class PresenterModule {
    @Provides
    @Singleton
    IModel provideModel(){
        return mock(Model.class);
    }

}

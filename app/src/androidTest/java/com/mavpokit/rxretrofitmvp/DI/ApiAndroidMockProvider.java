package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class ApiAndroidMockProvider {

    @Provides
    @Singleton
    StackoverflowApiInterface provideApiInterface() {
        return mock(StackoverflowApiInterface.class);
    }
}

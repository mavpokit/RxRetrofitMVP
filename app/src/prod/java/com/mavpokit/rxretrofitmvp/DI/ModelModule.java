package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class ModelModule {

    @Provides
    @Singleton
    StackoverflowApiInterface provideApiInterface(){
        return ApiModule.getApiInterface();
    }
}

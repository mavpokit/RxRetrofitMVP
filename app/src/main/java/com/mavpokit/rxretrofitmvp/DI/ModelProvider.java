package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class ModelProvider {
    @Provides
    @Singleton
    IModel provideModel(){
        return new Model();
    }

}

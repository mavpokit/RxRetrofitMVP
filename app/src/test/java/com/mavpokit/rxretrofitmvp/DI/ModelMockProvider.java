package com.mavpokit.rxretrofitmvp.DI;

import android.util.Log;

import com.mavpokit.rxretrofitmvp.Model.IModel;
import com.mavpokit.rxretrofitmvp.Model.Model;
import com.mavpokit.rxretrofitmvp.Model.Pojo.Answer;
import com.mavpokit.rxretrofitmvp.Model.Pojo.ListAnswer;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class ModelMockProvider {
    @Provides
    @Singleton
    IModel provideModel(){
        return mock(Model.class);
    }

}

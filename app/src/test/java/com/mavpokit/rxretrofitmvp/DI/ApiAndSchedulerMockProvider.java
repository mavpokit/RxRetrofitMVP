package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Const;
import com.mavpokit.rxretrofitmvp.Model.Model;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class ApiAndSchedulerMockProvider {

    @Provides
    @Singleton
    StackoverflowApiInterface provideApiInterface(){
        return mock(StackoverflowApiInterface.class);
    }
    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideIoScheduler(){
        return Schedulers.immediate();
    }
    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideUiScheduler(){
        return Schedulers.immediate();
    }

}

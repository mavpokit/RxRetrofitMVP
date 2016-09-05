package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

/**
 * Created by Alex on 02.09.2016.
 */
@Module
public class SchedulerMockProvider {

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

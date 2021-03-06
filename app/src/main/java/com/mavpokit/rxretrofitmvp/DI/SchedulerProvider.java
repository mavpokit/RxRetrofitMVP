package com.mavpokit.rxretrofitmvp.DI;

import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;
import com.mavpokit.rxretrofitmvp.Model.Const;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Alex on 02.09.2016.
 */
@Module
public class SchedulerProvider {

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideIoScheduler(){
        return Schedulers.io();
    }
    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }

}

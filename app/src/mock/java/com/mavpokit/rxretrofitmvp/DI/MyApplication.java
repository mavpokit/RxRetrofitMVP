package com.mavpokit.rxretrofitmvp.DI;

import android.app.Application;

/**
 * Created by Alex on 12.08.2016.
 */
public class MyApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=buildComponent();

    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder().build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}

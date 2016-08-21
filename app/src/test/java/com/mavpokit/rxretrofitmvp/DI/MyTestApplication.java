package com.mavpokit.rxretrofitmvp.DI;

import android.app.Application;

/**
 * Created by Alex on 12.08.2016.
 */
public class MyTestApplication extends MyApplication {
    private static AppTestComponent appTestComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appTestComponent=DaggerAppTestComponent
                .builder()
                .build();

    }

    public static AppTestComponent getAppTestComponent() {
        return appTestComponent;
    }
}

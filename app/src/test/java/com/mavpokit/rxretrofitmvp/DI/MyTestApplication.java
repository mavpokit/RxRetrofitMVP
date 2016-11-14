package com.mavpokit.rxretrofitmvp.DI;

import android.app.Application;

/**
 * Created by Alex on 12.08.2016.
 */
public class MyTestApplication extends MyApplication {
    @Override
    protected AppComponent buildComponent() {
        return DaggerAppTestComponent.builder().build();
    }

    @Override
    void initRealm() {
//        super.initRealm();
    }
}

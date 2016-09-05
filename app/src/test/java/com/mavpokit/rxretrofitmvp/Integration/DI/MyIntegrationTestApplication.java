package com.mavpokit.rxretrofitmvp.Integration.DI;

import com.mavpokit.rxretrofitmvp.DI.AppComponent;
import com.mavpokit.rxretrofitmvp.DI.MyApplication;

/**
 * Created by Alex on 12.08.2016.
 */
public class MyIntegrationTestApplication extends MyApplication {
    @Override
    protected AppComponent buildComponent() {
        return DaggerAppIntegrationTestComponent.builder().build();
    }
}

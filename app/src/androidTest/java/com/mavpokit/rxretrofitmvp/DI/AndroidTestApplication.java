package com.mavpokit.rxretrofitmvp.DI;

/**
 * Created by Alex on 12.08.2016.
 */
public class AndroidTestApplication extends MyApplication {
    @Override
    protected AppComponent buildComponent() {
        return DaggerUiTestComponent.builder().build();
    }
}

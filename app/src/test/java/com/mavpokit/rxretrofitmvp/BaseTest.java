package com.mavpokit.rxretrofitmvp;

import com.mavpokit.rxretrofitmvp.DI.AppTestComponent;
import com.mavpokit.rxretrofitmvp.DI.MyTestApplication;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Alex on 25.08.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = MyTestApplication.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public class BaseTest {

    protected AppTestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (AppTestComponent) MyTestApplication.getAppComponent();
    }

}



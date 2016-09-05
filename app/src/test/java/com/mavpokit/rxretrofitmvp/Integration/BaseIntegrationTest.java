package com.mavpokit.rxretrofitmvp.Integration;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.Integration.DI.AppIntegrationTestComponent;
import com.mavpokit.rxretrofitmvp.Integration.DI.MyIntegrationTestApplication;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Alex on 01.09.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = MyIntegrationTestApplication.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public class BaseIntegrationTest {

    protected AppIntegrationTestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (AppIntegrationTestComponent) MyIntegrationTestApplication.getAppComponent();
    }

}

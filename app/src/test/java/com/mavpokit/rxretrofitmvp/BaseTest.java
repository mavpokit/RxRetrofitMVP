package com.mavpokit.rxretrofitmvp;

import com.mavpokit.rxretrofitmvp.BuildConfig;
import com.mavpokit.rxretrofitmvp.DI.AppTestComponent;
import com.mavpokit.rxretrofitmvp.DI.MyTestApplication;
import com.mavpokit.rxretrofitmvp.Model.IModel;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

/**
 * Created by Alex on 25.08.2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(application = MyTestApplication.class,
        constants = BuildConfig.class, sdk = 19)
public class BaseTest {

    protected AppTestComponent component;

    @Before
    public void setUp() throws Exception {
        component = (AppTestComponent) MyTestApplication.getAppComponent();
    }

}

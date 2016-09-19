package com.mavpokit.rxretrofitmvp.Integration.DI;

import com.mavpokit.rxretrofitmvp.Integration.IntegrationApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.ApiModule;
import com.mavpokit.rxretrofitmvp.Model.Api.StackoverflowApiInterface;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;

/**
 * Created by Alex on 12.08.2016.
 */
@Module
public class IntegrationApiProvider {

    @Provides
    @Singleton
    StackoverflowApiInterface provideApiInterface(MockWebServer server){
        try {
            return new IntegrationApiModule().getApiInterface();
            //return new IntegrationApiModule().getApiInterface(server);
        } catch (IOException e) {
            throw new RuntimeException("Can't create ApiInterface: "+e.getLocalizedMessage());
        }
    }

    @Provides
    @Singleton
    MockWebServer provideMockWebServer(){
        return new MockWebServer();
    }

}

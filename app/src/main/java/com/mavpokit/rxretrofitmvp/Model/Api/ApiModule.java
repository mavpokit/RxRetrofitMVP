package com.mavpokit.rxretrofitmvp.Model.Api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 27.07.2016.
 */
public class ApiModule {
    public static StackoverflowApiInterface getApiInterface(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        StackoverflowApiInterface api = retrofit.create(StackoverflowApiInterface.class);
        return api;
    }

}

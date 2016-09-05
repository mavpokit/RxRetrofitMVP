package com.mavpokit.rxretrofitmvp.Model.Api;

//import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//import retrofit.GsonConverterFactory;
//import retrofit.Retrofit;
//import retrofit.RxJavaCallAdapterFactory;


/**
 * Created by Alex on 27.07.2016.
 */
public class ApiModule {
    public static StackoverflowApiInterface getApiInterface(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        StackoverflowApiInterface api = retrofit.create(StackoverflowApiInterface.class);
        return api;
    }

}

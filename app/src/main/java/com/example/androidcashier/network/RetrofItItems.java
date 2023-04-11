package com.example.androidcashier.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofItItems {

    private  static final String Base_url="http://scopos-arch.cloud/";
    private ApisCalls calls;
    private static RetrofItItems Instance;
    private Retrofit retrofit;

    public RetrofItItems() {

            retrofit=new Retrofit.Builder()
                    .baseUrl(Base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

    }

    public static RetrofItItems getInstance(){
        if( null ==Instance)
            Instance=new RetrofItItems();
        return Instance;
    }


    public ApisCalls getApiCalls(){
        return calls= retrofit.create(ApisCalls.class);

    }
}

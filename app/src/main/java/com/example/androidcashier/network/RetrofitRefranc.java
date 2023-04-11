package com.example.androidcashier.network;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRefranc {
    private  static final String Base_url="http://scopos-uat-5.online/";
    private ApisCalls calls;
    private static RetrofitRefranc Instance;
    private Retrofit retrofit;

    public RetrofitRefranc() {

        retrofit=new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();


    }

    public static RetrofitRefranc getInstance(){
        if( null ==Instance)
            Instance=new RetrofitRefranc();
        return Instance;
    }


    public ApisCalls getApiCalls(){
        return calls= retrofit.create(ApisCalls.class);

    }
}

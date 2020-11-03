package com.example.likegithubrepoproject.Retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private static String base_Url = "https://api.github.com/";


    //retrofit istek
    public static Retrofit getClient(){

        if(retrofit== null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            return retrofit;
        }
        return retrofit;
    }

}

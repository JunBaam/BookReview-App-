package com.example.bookreview;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//aws 레트로핏 연결
 class ApiClient {

    private  static final String BaseUrl = "http://15.165.60.40/user/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){


        if(retrofit==null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }


}

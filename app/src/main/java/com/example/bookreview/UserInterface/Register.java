package com.example.bookreview.UserInterface;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Register {

    final static String REGIURL= "http://15.165.60.40/user/";

    @FormUrlEncoded
    @POST("register3.php")
    Call<String> getUserRegi(

            @Field("key") String key,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            //추가
            @Field("picture") String picture


    );

}


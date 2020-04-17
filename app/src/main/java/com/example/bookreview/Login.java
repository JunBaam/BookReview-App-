package com.example.bookreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Login {



    String LOGINURL = "http://15.165.60.40/user/";
    @FormUrlEncoded
    @POST("login.php")
    Call<String> getUserLogin(

            @Field("email") String email,
            @Field("password") String password);


    @POST("get_user.php")
    Call<List<String>> getUser();

}
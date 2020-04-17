package com.example.bookreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserApi {

    //db에서 값을 가져옴
   // @POST("get_user.php")
    //Call<String> getUserInfo;


     //프로필 이미지 , 아이디 수정
    @FormUrlEncoded
    @POST("update_profile.php")
    Call<String> updateProfile(
            @Field("image") String image,
            @Field("id") String id);


    //레트로핏으로는 요청을하는것!

//    @FormUrlEncoded
////    @POST("get_user.php")
////    Call<UserItem> getUserValue(
////            @Field("name") String name
////    );

    String REGIURL = "http://15.165.60.40/user/";
    @FormUrlEncoded
    @POST("get_profile.php")
    Call<String> getUserValue(
            // @Field("name") String name,
            @Field("name") String name
    );






}

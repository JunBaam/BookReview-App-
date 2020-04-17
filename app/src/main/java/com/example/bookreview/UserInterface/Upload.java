package com.example.bookreview.UserInterface;

import com.example.bookreview.ImageClass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface Upload {


  @POST("upload.php")
  @FormUrlEncoded
    Call<RegisterItem> uploadImage(
            @Field("img_name") String img_name,
            @Field("image") String image,
            @Field("email") String email);


}

package com.example.bookreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ReplyApi {

    @FormUrlEncoded
    @POST("add_reply.php")
    Call<UserItem> InserReply(

            @Field("picture") String picture,
            @Field("name") String name,
            @Field("reply") String reply,
            @Field("time") String time,
            @Field("isbn") String isbn
            );

    //리뷰를 가져옴

    @FormUrlEncoded
    @POST("get_reply.php")
    Call<List<UserItem>> GetReply(
           @Field("isbn") String isbn

    );

//    @FormUrlEncoded
//    @POST("get_reply.php")
//    Call<String> GetReply(
//            @Field("isbn") String isbn
//    );

}

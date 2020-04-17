package com.example.bookreview;

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
            @Field("time") String time



    );

}

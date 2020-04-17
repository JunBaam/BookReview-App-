package com.example.bookreview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ReviewInterface {

       //db에서 값을 가져옴
    @POST("get_review.php")
    Call<List<ReviewItem>> getReview();


    //db에 리뷰를 입력
    @FormUrlEncoded
    @POST("insert_review.php")
    Call<ReviewItem> insert(
            @Field("title") String title,
            @Field("image") String image,
            @Field("author") String author,
            @Field("user") String user,
            @Field("isbn") String isbn,
            @Field("star") String star,
            @Field("content") String content

    );

    //북마크 여부
    @FormUrlEncoded
    @POST("update_review.php")
    Call<ReviewItem> update(
            @Field("author") String author,
            @Field("love") boolean love);


    //리뷰 삭제
  //해당 아이디에 맞는  게시물을 삭제
    @FormUrlEncoded
    @POST("delete_review.php")
    Call<ReviewItem> delete_review(

            @Field("user") String user,
            @Field("title") String title);

    //리뷰 수정
    @FormUrlEncoded
    @POST("modify_review.php")
    Call<ReviewItem> modify_review(

            @Field("title") String title,
            @Field("content") String content,
            @Field("star") String star);





}

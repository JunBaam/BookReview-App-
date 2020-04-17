package com.example.bookreview.NaverAPi;
import com.example.bookreview.BookSearchModel.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"X-Naver-Client-Id:Xrbd5nyqx3rd4zUXkIN5", "X-Naver-Client-Secret:Kg2t51sVL1"})
    @GET("book.json")
    Call<Book> getBooks(@Query("query") String title,
                         @Query("display") int displaySize,
                         @Query("start") int startPosition);


}
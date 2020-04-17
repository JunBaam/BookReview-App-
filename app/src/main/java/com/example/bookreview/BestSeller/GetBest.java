package com.example.bookreview.BestSeller;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBest {
//    {
//        "id": "23",
//            "title": "흔한남매. 4",
//            "author": "흔한남매(원작)",
//            "image": "http:\/\/image.kyobobook.co.kr\/images\/book\/large\/847\/l9791164134847.jpg",
//            "url": "http:\/\/www.kyobobook.co.kr\/product\/detailViewKor.laf?mallGb=KOR&ejkGb=KOR&barcode=9791164134847",
//            "prcie": "12000"
//    },



    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("author")  //Json 응답에서 각각의 필드를 구분하기 위해 사용
    String author;

    @SerializedName("image")
    String image;

    @SerializedName("url")
    String url;

    @SerializedName("price")
    String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}

package com.example.bookreview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class ReviewItem {

        /*
        @SerializedName Gson이 Json 키를 필드에 매핑하기위해 사용
        @Expose  이 필드가 JSON 직렬화 또는 비 직렬화에 노출되어야 함을 나타냅니다.
         */

    //제목,이미지 ,저자 , isbn(고유번호) , 내용, 별점, 사용자  7개

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("author")
    private String author;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("content")
    private String content;

    @SerializedName("star")
    private String star;

    @SerializedName("user")
    private String user;

    //북마크 유무
    @SerializedName("love")
    private Boolean love;

    //북마크 유무
    @SerializedName("like")
    private Boolean like;


    @SerializedName("id")
    private int id;

    @SerializedName("user_image")
    private String user_image;

    @SerializedName("date")
    private String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_image() {
        return user_image;
    }
    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLove() {
        return love;
    }

    public void setLove(Boolean love) {
        this.love = love;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }




    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @SerializedName("message")
    private String massage;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
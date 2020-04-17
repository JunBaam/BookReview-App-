package com.example.bookreview;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    /*
    사용자 ,사용자 프로필, 댓글 ,시간 ,isbn

     */
    @SerializedName("user")
    private String user;

    @SerializedName("image")
    private String image;


    @SerializedName("reply")
    private String reply;

    @SerializedName("time")
    private String time;

    @SerializedName("isbn")
    private String isbn;

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

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

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUser() {
        return user;
    }

    public void setName(String user) {
        this.user = user;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }
}

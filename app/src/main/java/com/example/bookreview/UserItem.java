package com.example.bookreview;

import com.google.gson.annotations.SerializedName;

public class UserItem {

    /*
    사용자 프로필 댓글

     */
    @SerializedName("name")
    private String name;

    @SerializedName("picture")
    private String picture;

    @SerializedName("reply")
    private String reply;


    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

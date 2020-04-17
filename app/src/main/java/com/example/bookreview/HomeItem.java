package com.example.bookreview;

public class HomeItem {

    /*
    제목,저자,이미지,사용자,후기내용,점수,좋아요수,답글수.

     */

    String title;
    String author;
    String image;
    String user;
    String content;
    String star;
    int like_count;
    int reply_count;

    public HomeItem(String title,String author,String image,String user,String star
    ,int like_count,int reply_count){
        this.title=title;
        this.author=author;
        this.image=image;
        this.user=user;
        this.star=star;
        this.like_count=like_count;
        this.reply_count=reply_count;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }
}

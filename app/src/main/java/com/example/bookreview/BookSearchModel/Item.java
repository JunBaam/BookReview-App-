
package com.example.bookreview.BookSearchModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*
사용할것 (8개)
제목
링크
이미지
저자
가격 (정가)
출간일\
출판사
내용
 */


public class Item {
    /*
    @SerializedName Gson이 Json 키를 필드에 매핑하기위해 사용
    @Expose  이 필드가 JSON 직렬화 또는 비 직렬화에 노출되어야 함을 나타냅니다.

     */


    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("pubdate")
    @Expose
    private String pubdate;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    @SerializedName("isbn")
    @Expose
    private String isbn;

    public Item(String ee, String gg) {
    }

/*
    @SerializedName("discount")
    @Expose
    private String discount;
 */


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /*
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
*/

}

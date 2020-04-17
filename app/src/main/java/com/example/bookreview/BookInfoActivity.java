package com.example.bookreview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/*
책의 정보를 볼수있는 액티비티
검색 api로 찾은정보를 확인할수있게 만듬
정보(8개)
제목,링크,이미지,저자,가격(정가),출간일,내용,출판사
  */

public class BookInfoActivity extends AppCompatActivity {
    private static final String TAG = "BookInfoActivity에서 값을받음 " ;

    //TODO: 2020-03-25  uri 링크도 고려해야됨. 하나더

    //제목,저자,가격,출판사,출판일,내용  6개
    private TextView tv_title ,tv_author, tv_price ,tv_publisher, tv_date , tv_content;
    //책 표지
    private ImageView iv_poster;
    //리뷰등록버튼
    private Button btn_review;
    String link ,image,title, isbn,author;



    public BookInfoActivity() {
    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info);

        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김
        Intent intent= getIntent();

            tv_title=findViewById(R.id.tv_info_title);
            tv_author=findViewById(R.id.tv_info_author);
            tv_publisher=findViewById(R.id.tv_info_publisher);
            //tv_price=findViewById(R.id.tv_info_price);
            tv_content=findViewById(R.id.tv_info_contents);
            tv_date=findViewById(R.id.tv_info_date);
            iv_poster =findViewById(R.id.iv_info_poster);



        //Html.fromHtml html 테그들을 없애준다!!!
        tv_title.setText(Html.fromHtml(intent.getStringExtra("book_title")));
        tv_date.setText(Html.fromHtml(intent.getStringExtra("book_date")));
        tv_content.setText(Html.fromHtml(intent.getStringExtra("book_contents")));
        //tv_price.setText(intent.getStringExtra("book_price"));
        tv_publisher.setText(Html.fromHtml(intent.getStringExtra("book_publish")));
        tv_author.setText(Html.fromHtml(intent.getStringExtra("book_author")));

        //링크
        link=intent.getStringExtra("book_link");
        //이미지
        image =intent.getStringExtra("book_image"); //string 형식으로 URL 을 받음
        Glide.with(this).load(image).into(iv_poster); //받은 URL을 글라이드를 통해 iv_poster 이미지뷰에 적용
       //저자
        author= intent.getStringExtra("book_author");
       //고유번호
        isbn = intent.getStringExtra("book_isbn");
        //책제목
        title = String.valueOf(Html.fromHtml(intent.getStringExtra("book_title")));


            //리뷰작성하기 버튼 클릭시 책정보를 BookReviewActivity 로보냄
            btn_review=findViewById(R.id.btn_info_review);
            btn_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(BookInfoActivity.this , BookReviewActivity.class);

                    //책제목
                    intent.putExtra("title",title);
                    //isbn (고유번호)
                    intent.putExtra("isbn",isbn);
                    //이미지
                    intent.putExtra("image",image);
                    //저자
                    intent.putExtra("author",author);

                    startActivity(intent);

                }
            }); //btn_review











    }// onCreate





}// BookInfoActivity class

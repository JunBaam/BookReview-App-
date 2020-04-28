package com.example.bookreview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookReviewActivity extends AppCompatActivity {

    private TextView tv_review_title, tv_rating_count; //책제목 ,레이팅바점수
    private  EditText et_review_content;//후기작성 et
    private Button btn_review; //리뷰 btn
    private RatingBar rb_review; //레이팅바
    private String image,title, isbn,author,star, content ,user;
    private PreferenceHelper preferenceHelper;

    private static final String TAG = "책리뷰액티비티  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_review);

        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김
        Intent intent= getIntent();

        preferenceHelper = new PreferenceHelper(this);

        et_review_content=findViewById(R.id.et_review_content);
        tv_review_title=findViewById(R.id.tv_review_title);
        tv_rating_count=findViewById(R.id.tv_rating_count);


        //별(레이팅바)에 점수를 연결함.
        rb_review=findViewById(R.id.rb_review_rating);
        rb_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv_rating_count.setText(""+v); }


        });

        //별점점수를 받아옴
        star= (String) tv_rating_count.getText();

       tv_review_title.setText(intent.getStringExtra("title"));

        Log.i(TAG, "BookkInfoActivity로부터 값을받음" +
                isbn+"###"+author+"###"+image+"###"+title);

        //리뷰 등록 btn 클릭시 홈화면으로 이동
        btn_review=findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                postData(); //책리뷰 정보를 DB에 넣는다.

                Intent intent = new Intent(BookReviewActivity.this,MainActivity.class);

                //기존에존재하던 홈화면을 불러오고 나머지 스택을 모두 제거
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    } //onCreate

    //Insert 리뷰
    private void postData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("리뷰등록..");
        progressDialog.show();

        et_review_content=findViewById(R.id.et_review_content);
        tv_review_title=findViewById(R.id.tv_review_title);
        tv_rating_count=findViewById(R.id.tv_rating_count);

        //후기작성내용을 받아옴.
        content = String.valueOf(et_review_content.getText());
        //별점점수를 받아옴
        star= (String) tv_rating_count.getText();
        //현재 로그인한 유저
        user = preferenceHelper.getName();

        //BookInfoActivity 로부터 책정보를 받음
        Intent intent =getIntent();
        isbn =intent.getStringExtra("isbn");
        author =intent.getStringExtra("author");
        image =intent.getStringExtra("image");
        title = intent.getStringExtra("title");

        //현재시간 가져오기 년 월 일 시간 분
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String getTime = sdf.format(date);


        ReviewInterface apIInterface=ApiClient.getApiClient().create(ReviewInterface.class);

        Call<ReviewItem> call =apIInterface.insert(title,image,author,user,isbn,star,content,getTime);

        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {
                progressDialog.dismiss();

                Log.i(BookReviewActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")) {
                    finish();
                } else {
                    Toast.makeText(BookReviewActivity.this, "리뷰등록", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReviewItem> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookReviewActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });





    } //postData


}//class

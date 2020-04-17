package com.example.bookreview.BestSeller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bookreview.BookReviewActivity;
import com.example.bookreview.MainActivity;
import com.example.bookreview.PreferenceHelper;
import com.example.bookreview.R;

public class BestReviewActivity extends AppCompatActivity {

    private TextView tv_review_title, tv_rating_count; //책제목 ,레이팅바점수
    private  EditText et_review_content;//후기작성 et
    private Button btn_review; //리뷰 btn
    private RatingBar rb_review; //레이팅바
    private String title,  content ,user,star;

    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_review);
        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김


        Intent intent= getIntent();

        tv_rating_count=findViewById(R.id.tv_rating_count);
        tv_review_title=findViewById(R.id.tv_review_title);

        tv_review_title.setText(intent.getStringExtra("best_title"));


        //별(레이팅바)에 점수를 연결함.
        rb_review=findViewById(R.id.rb_review_rating);
        rb_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                tv_rating_count.setText(v+"점");

            }
        });

        //리뷰 등록 btn 클릭시 홈화면으로 이동
        btn_review=findViewById(R.id.btn_review);
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BestReviewActivity.this, MainActivity.class);

                //후기작성내용을 받아옴.
                content = String.valueOf(et_review_content.getText());
                //별점점수를 받아옴
                star= (String) tv_rating_count.getText();


                //기존에존재하던 홈화면을 불러오고 나머지 스택을 모두 제거
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));


            }
        });


    }//onCreate
}

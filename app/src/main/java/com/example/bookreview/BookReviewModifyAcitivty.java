package com.example.bookreview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookReviewModifyAcitivty extends AppCompatActivity {


    private static final String TAG = "리뷰수정";
    TextView tv_modify_title,tv_rating_count;
    EditText et_modify_content;
    String title,content,star;
    RatingBar rb_star;
    Button btn_modify;

    ReviewInterface reviewInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_review_modify);

        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김

        Intent intent =getIntent();
  title=    intent.getStringExtra("title").trim();
   content=  intent.getStringExtra("content").trim();
   star= intent.getStringExtra("star").trim();


        tv_modify_title=findViewById(R.id.tv_review_modify_title);
         tv_modify_title.setText(title);

         tv_rating_count=findViewById(R.id.tv_modify_rating_count);
         tv_rating_count.setText(star);

         et_modify_content=findViewById(R.id.et_review_modify_content);
         et_modify_content.setText(content);

         rb_star=findViewById(R.id.rb_modify_review_rating);
         rb_star.setRating(Float.parseFloat(star));

         rb_star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                 tv_rating_count.setText(""+v);
             }
         });


         btn_modify=findViewById(R.id.btn_modify_review);
         btn_modify.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                // Log.i(TAG, title+content+star);

                      //수정된 리뷰내용 , 별점
                      String modify_content= String.valueOf(et_modify_content.getText());
                      String modify_star = String.valueOf(tv_rating_count.getText());

                 /*
                 업데이트는 되는데 수정된 String값이 업로드가 안됨.
                  */

                 UpdateReview(title,modify_content,modify_star);
                 Log.i(TAG, "보내느값: "+title+modify_content+modify_star);

                 finish();
             }
         });

    }//onCreate




    //게시물 삭제
    private void UpdateReview( final String title, String content,String star) {

        reviewInterface = ApiClient.getApiClient().create(ReviewInterface.class);

        Call<ReviewItem> call =  reviewInterface.modify_review(title,content,star);

        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                Log.i("리뷰수정", response.body().toString());


                if (value.equals("1")){
                    Toast.makeText(BookReviewModifyAcitivty.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookReviewModifyAcitivty.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ReviewItem> call, Throwable t) {
              //  Toast.makeText(BookReviewModifyAcitivty.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure: ");
            }
        });

    }







    private void updateReview(final String title) {

        Intent intent =getIntent();

        final String content=  intent.getStringExtra("content").trim();
        final String star= intent.getStringExtra("star").trim();

        reviewInterface =ApiClient.getApiClient().create(ReviewInterface.class);

        Log.i(TAG, title+content+star);
        Call<ReviewItem> call = reviewInterface.modify_review(title,content,star);

        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

                Log.i(BookReviewModifyAcitivty.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(BookReviewModifyAcitivty.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookReviewModifyAcitivty.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewItem> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });



    }
}//class

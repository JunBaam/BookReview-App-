package com.example.bookreview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookreview.UserInterface.Upload;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {

    EditText Img_title;
    Button BnChoose, BnUpload;
    ImageView Img;

    private static final int REQUEST_CODE = 0;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        Img_title = findViewById(R.id.img_title);
        BnChoose = findViewById(R.id.chooseBn);
        BnUpload = findViewById(R.id.uploadBn);
        Img = findViewById(R.id.imageView);

        BnChoose.setOnClickListener(this);
        BnUpload.setOnClickListener(this);

        EditText edit_text= findViewById(R.id.et_test);
        RatingBar rating = findViewById(R.id.rb_test);


        String edit= String.valueOf(edit_text.getText());

        rating.setRating(Float.parseFloat(edit));



        //resultText = (TextView) findViewById(R.id.resultText);
        //ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        //ratingBar.setRating(rate);

        //float rate = Float.valueOf(getIntent().getExtras().get("edtRating").toString());














    }//onCreate

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.chooseBn:
                chooseImage();


                break;


            case R.id.uploadBn:
                break;

        }

    }

    //이미지 선택 메서드  chooseImage()
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    Img.setImageBitmap(img);

                    Img.setVisibility(View.VISIBLE);
                    Img_title.setVisibility(View.VISIBLE);
                    BnChoose.setEnabled(false);
                    BnUpload.setEnabled(true);

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    } //onActivityResult 종료


    // 비트맵 이미지를 jpeg로 바꿈 .
    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void uploadImage() {
       // String Image = getStringImage();
        String Title = Img_title.getText().toString();

        Upload apiInterface = ApiClient.getApiClient().create(Upload.class);
     //   Call<ImageClass> call = apiInterface.uploadImage(Title, Image);

    }




}
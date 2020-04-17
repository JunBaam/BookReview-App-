package com.example.bookreview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MypageActivity extends AppCompatActivity {



    TextView tv_name  ,tv_board_count;
    Button btn_modify;

    private CircleImageView iv_profile;       //프로필 이미지뷰
    private FloatingActionButton btn_gallery; //갤러리 버튼


    PreferenceHelper preferenceHelper;  //쉐어드

    private static final int REQUEST_CODE = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        /*
        쉐어드에 저장되어있는 로그인 정보를 가져온다.
         */
        preferenceHelper = new PreferenceHelper(this);

        tv_name =findViewById(R.id.tv_name);
        tv_name.setText(preferenceHelper.getName());

        iv_profile =findViewById(R.id.iv_profile);
        btn_gallery=findViewById(R.id.btn_gallery);


        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });  //btn_gallery 종료



    } //onCreate 종료


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

                    iv_profile.setImageBitmap(img);
                } catch (Exception e) {
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    } //onActivityResult 종료



}

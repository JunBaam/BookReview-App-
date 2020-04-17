package com.example.bookreview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookreview.UserInterface.RegisterItem;
import com.example.bookreview.UserInterface.Upload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MypageModifyActivity extends AppCompatActivity {

     /*
     서버에  맞는 값에다 update해야되고  (id,image)
     쉐어드에 값도 갱신               */

      EditText et_idModify;
      ImageView iv_profileModify;
      Button btn_modify;

      PreferenceHelper preferenceHelper;
      SharedPreferences sharedPreferences;
      UserApi userApi;
      Uri ImageUri;
    Upload upload;

    Context context;
    ApiClient apiClient;
      Bitmap bitmap;
    
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int CAPTURE_REQUEST_CODE = 0;
    private static final int SELECT_REQUEST_CODE = 1;
    private final String BASEURL = "http://15.165.60.40/user/";
    private String TAG ="프로필수정화면 ";
    String modify_user;
    String profileImage ,user,email; //프로필이미지 , 사용자를 담는 변수
    String end , sh_image; //서버에 올라가는 최종 id , 쉐어드에서 가져오는 이미지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_modify);
        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://15.165.60.40/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        upload = retrofit.create(Upload.class);


         et_idModify=findViewById(R.id.et_modify_name);
         iv_profileModify=findViewById(R.id.iv_modify_image);
         btn_modify=findViewById(R.id.btn_profile_modify);


       //현재 로그인 정보를 가져옴 (아이디 ,이메일 )
         sharedPreferences = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
         user = sharedPreferences.getString("id","");
         email = sharedPreferences.getString("email","");
       //profileImage = sharedPreferences.getString("image","");

        //쉐어드에 저장된 아이디를 edtitext에 뿌림
        et_idModify.setText(user);

        //FragMypage로부터  이미지 를 받아옴
        Intent intent =getIntent();
        intent.getStringExtra("image");
        profileImage =  intent.getStringExtra("image");
        Log.i(TAG, "받아온이미지 " + profileImage);

        Glide.with(this)
                .load(BASEURL+profileImage.trim())
                .skipMemoryCache(true)
                .into(iv_profileModify);

        //수정완료버튼
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //버튼클릭시
                et_idModify =findViewById(R.id.et_modify_name);
                end=  String.valueOf(et_idModify.getText());

                //로그인id정보 수정
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id",    end);
                editor.apply();

                Log.i(TAG, "쉐어드"+ modify_user +"최종edit"+  end);

                //레트로핏선언

                ImageUpload(bitmap);  //이미지를 서버에 업로드
                //프로필 이미지를 쉐어드에 저장 .
             //   sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
             //  SharedPreferences.Editor edit = sharedPreferences.edit();

                //비트맵을 스트링으로 바꿈
                //항상 생각하자 비트맵을 스트링으로 바꿔서 저장하면 긴문자열이 된다. 확인학것!
            //    profileImage = BitmapToString(bitmap);
             //   edit.putString("image", profileImage);
              //  edit.apply();

                finish();

            }
        });


        //이미지 선택
        iv_profileModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckPermission()) {
                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(select, SELECT_REQUEST_CODE);


               }
            }
        });


    }//oncreate



    //이미지 업로드 메서드
    private void ImageUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30,byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        String  img_name = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Log.i(TAG, "ImageUpload: "+    end);

        //현재 로그인된 사용자의 이름으로 이미지 가 올라감
        Call<RegisterItem> call = upload.uploadImage(end,image,email);   //사용자 id
        call.enqueue(new Callback<RegisterItem>() {
            @Override
            public void onResponse(Call<RegisterItem> call, Response<RegisterItem> response) {
                Toast.makeText(MypageModifyActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<RegisterItem> call, Throwable t) {
                Toast.makeText(MypageModifyActivity.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

   //비트맵을 스트링으로바꿈
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }

    //스트링을 비트맵으로 변경
    public Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            Log.i("your tag", "Exception has occurred: ", e);
            return null;
        }
    }


    //갤러리에서 이미지를 가져옴
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SELECT_REQUEST_CODE:
            {
                if(resultCode == RESULT_OK){
                    try {
                         ImageUri = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
                        iv_profileModify.setImageBitmap(bitmap);

                        /*
                        profileImage변수에  갤러리에서 가져온 bitmap 이미지를
                        String형태로 저장하였다.
                         */
                        Log.i(TAG, "갤러리uri" +ImageUri);
                        Log.i(TAG, "갤러리bit" +bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }

    }

     //권한허가
    public boolean CheckPermission() {
        if (ContextCompat.checkSelfPermission(MypageModifyActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MypageModifyActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MypageModifyActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MypageModifyActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(MypageModifyActivity.this,
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(MypageModifyActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(MypageModifyActivity.this)
                        .setTitle("Permission")
                        .setMessage("Please accept the permissions")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MypageModifyActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                                startActivity(new Intent(MypageModifyActivity
                                        .this,MypageModifyActivity.class));
                                MypageModifyActivity.this.overridePendingTransition(0, 0);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(MypageModifyActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}//class 

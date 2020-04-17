package com.example.bookreview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RegisterActivity extends AppCompatActivity {

    private EditText et_email, et_name ,et_password;
    private TextView tv_login;
    private Button btn_register;
    private PreferenceHelper preferenceHelper;

    private CircleImageView iv_profile;       //프로필 이미지뷰
    private FloatingActionButton btn_gallery; //갤러리 버튼

    private Bitmap bitmap;
    private static final int REQUEST_CODE = 0; //갤러리 이미지 Request_code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
        actionBar.hide(); //상단액션바를 숨김

        preferenceHelper = new PreferenceHelper(this);

        //회원가입시. LoginActivity로 이동
        if(preferenceHelper.getIsLogin()){
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }

        et_name =findViewById(R.id.et_name);
        et_email =findViewById(R.id.et_email);
        et_password =findViewById(R.id.et_password);
        iv_profile =findViewById(R.id.iv_profile);
        //프로필 이미지 선택 버튼
        iv_profile=findViewById(R.id.iv_profile);
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }
        });  //btn_gallery 종료

        //로그인하기 버튼
        tv_login =findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        //회원가입 버튼
        btn_register =findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerMe("insert");


            }
        }); //btn_register 종료

    }// onCreate 종료



    //이미지 선택 메서드
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
                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                    iv_profile.setImageBitmap(bitmap);

                } catch (Exception e) {
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    } //onActivityResult 종료

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //회원가입메서드 registerMe
    private void registerMe(final String key){
        final String name = et_name.getText().toString();
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();

        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);

        }

        //레트로핏시작.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Register.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Register api = retrofit.create(Register.class);
        // picture 추가
        Call<String> call = api.getUserRegi(key,name,password,email,picture);


        call.enqueue(new Callback<String>() {
            @Override
            //통신 성공시.
            //응답 내용 (body)를 받아온다 .
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("회원정보 응답내용", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();


                        Toast.makeText(RegisterActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                        try {
                            //회원가입정보 파싱데이터
                            parseRegData(jsonresponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("onEmptyResponse", "값이 없습니다.");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            //통신 실패시.
            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });  //enqueue 종료

    }//registerMe 종료

      /*회원가입정보 파싱데이터 메서드
        회원가입 성공시 LoginActivity로 넘어감
       */


    private void parseRegData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){
            saveInfo(response);

            Toast.makeText(RegisterActivity.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();

        }else {
            Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    //저장정보 메서드 putIsLogin을 true로 바꾼다.
    private void saveInfo(String response){

        preferenceHelper.putIsLogin(true);

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");


                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    preferenceHelper.putName(dataobj.getString("name"));
                    preferenceHelper.putMail(dataobj.getString("email"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

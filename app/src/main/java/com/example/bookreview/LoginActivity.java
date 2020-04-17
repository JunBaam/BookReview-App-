package com.example.bookreview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookreview.Adapter.HomeAdpater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email ,et_password;
    private TextView tv_join;
    private Button btn_login;
    private PreferenceHelper preferenceHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //상단바 삭제
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        preferenceHelper = new PreferenceHelper(this);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        tv_join = findViewById(R.id.tv_join);



        //회원가입하기
        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferenceHelper.putIsLogin(false);

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);

            }
        });  // tv_join 종료 

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();

            }
        });  //btn_login 종료 


    }//onCreate 종료 
    
    
        private void loginUser() {

            final String email = et_email.getText().toString().trim();
            final String password = et_password.getText().toString().trim();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Login.LOGINURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            Login api = retrofit.create(Login.class);

            Call<String> call = api.getUserLogin(email,password);

            //비동기적 응답받기.
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    //응답내용 출력
                    Log.i("Responsestring", response.body().toString());
                    //Toast.makeText()
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i("onSuccess", response.body().toString());

                            String jsonresponse = response.body().toString();
                            parseLoginData(jsonresponse);

                        } else {
                            Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

        }


        private void parseLoginData(String response){
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("true")) {

                    saveInfo(response);
                    Toast.makeText(LoginActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

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

}//LoginActivity 종료

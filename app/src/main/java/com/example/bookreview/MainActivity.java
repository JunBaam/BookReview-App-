package com.example.bookreview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.bookreview.BottomNavi.SearchFragment;
import com.example.bookreview.BottomNavi.FragChat;
import com.example.bookreview.BottomNavi.FragHome;
import com.example.bookreview.BottomNavi.FragMypage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀네비
    private FragmentManager fm;
    private FragmentTransaction ft;

    private FragHome fragHome;
    private  SearchFragment searchFragment;
    private FragChat fragChat;
    private FragMypage fragMypage;
    ImageView iv_ocr;
    TextView tv_user;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        sharedPreferences = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("id","");
        tv_user=findViewById(R.id.tv_main_user);
        tv_user.setText(user+"님 안녕하세요 :)");



        iv_ocr=findViewById(R.id.iv_ocr);
        iv_ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent =new Intent(getApplicationContext() ,OcrActivity.class);
            startActivity(intent);

            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_register:
                        setFrag(1);
                        break;
                    case R.id.action_chat:
                        setFrag(2);
                        break;
                    case R.id.action_mypage:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        fragHome = new FragHome();
        searchFragment = new SearchFragment();
        fragChat =new FragChat();
        fragMypage =new FragMypage();

        setFrag(0); // 첫번째 플래그먼트 화면을 무엇으로 지정할지 선택.

    } //oncreate


    //플래그먼트 지정 , 교체가 일어나는 실행문.
    private void setFrag(int n){
        fm = getSupportFragmentManager();


        ft =fm.beginTransaction(); //실직적인 화면 교체하는 역할을 담당
        switch (n){
            case 0:
                ft.replace(R.id.main_frame ,fragHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame ,searchFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame ,fragChat);
                ft.commit();
                break;
            case 3:

                if(fragMypage.isAdded()){
                    ft.remove(fragMypage);
                    fragMypage=new FragMypage();

                }

               ft.add(fragMypage,"mypage");
               ft.replace(R.id.main_frame , fragMypage) ;
               // ft.add(FragMypage,"mypage")
                ft.commit();
                break;

        }





    }



} //class

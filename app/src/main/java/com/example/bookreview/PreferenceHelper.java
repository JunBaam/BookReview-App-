package com.example.bookreview;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;


/*
로그인시 쉐어드에 사용자의 아이디를 저장한다.
 */

public class PreferenceHelper {

    private final String INTRO = "intro";  //로그인유무 판별
    private final String NAME = "id";
    private final String EMAIL = "email";
    private final String IMAGE= "image";

    private SharedPreferences login_prefs;
    private Context context;


    // loginInfo 쉐어드 생성
    public PreferenceHelper(Context context) {
        login_prefs = context.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        this.context = context;
    }



    //Intro 저장
    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = login_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.apply();
    }

 /*로그인 유무  기본값은 false
   로그인시 ture 로 변경.
  */

    public boolean getIsLogin() {
        return login_prefs.getBoolean(INTRO, false);
    }


    //로그인시 Name(email) 을 쉐어드에 저장
    public void putName(String loginorout) {
        SharedPreferences.Editor edit = login_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.apply();
    }

    public String getName() {
        return login_prefs.getString(NAME, "");

    }

    public void putMail(String loginorout) {
        SharedPreferences.Editor edit = login_prefs.edit();
        edit.putString(EMAIL, loginorout);
        edit.apply();
    }

    public String getMail() {
        return login_prefs.getString(EMAIL, "");
    }


    //이미지를 넣고 가져옴
    public void putImage(String loginorout) {
        SharedPreferences.Editor edit = login_prefs.edit();
        edit.putString(IMAGE, loginorout);
        edit.apply();
    }

    public String getImage() {
        return login_prefs.getString(IMAGE, "");
    }



 }

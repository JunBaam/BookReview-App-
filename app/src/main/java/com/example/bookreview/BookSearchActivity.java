package com.example.bookreview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookreview.BottomNavi.SearchFragment;

public class BookSearchActivity extends AppCompatActivity {


   // 책을 검색하는 액티비티

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);

        //책 검색 플래그먼트
        SearchFragment searchFragment = new SearchFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout_contents, searchFragment);
        transaction.commit();

    } //onCreate




}//class

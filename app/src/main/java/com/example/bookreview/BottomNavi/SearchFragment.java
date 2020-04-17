package com.example.bookreview.BottomNavi;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreview.R;
import com.example.bookreview.Adapter.BookAdapter;
import com.example.bookreview.BookSearchModel.Book;
import com.example.bookreview.BookSearchModel.Item;
import com.example.bookreview.NaverAPi.ApiInterface;
import com.example.bookreview.NaverAPi.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "책정보확인";
    private RecyclerView RvBooks ;
    private RecyclerView.LayoutManager mLayoutManager;

    private  BookAdapter bookAdapter;
    private EditText EtKeyword;  //검색창
    private Button BtnSearch;   //검색버튼
    private InputMethodManager mInputMethodManager;    //검색창 클릭시 키보드가 나타남

    public SearchFragment() {

    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.frag_book, container, false);
        setupSearchRV(root);     //검색 리사이클러뷰
        setupSearchView(root);   //검색창 클릭시

        return root;
    }




    //검색 리사이클러뷰
    private void setupSearchRV(View view) {
      RvBooks = view.findViewById(R.id.rv_books);
        RvBooks.setHasFixedSize(true);    //리사이클러뷰의 사이즈를 고정
        mLayoutManager = new LinearLayoutManager(getContext());
        RvBooks.setLayoutManager(mLayoutManager);

        // 어댑터 연결
        ArrayList<Item> books = new ArrayList<>();
        bookAdapter = new BookAdapter(getContext(), books);
        RvBooks.setAdapter(bookAdapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        RvBooks.addItemDecoration(dividerItemDecoration);

    }  //setupRecyclerView


    //검색하는부분  검색창 ,검색버튼
    private void setupSearchView(View view) {
        EtKeyword = view.findViewById(R.id.et_keyword);
        BtnSearch = view.findViewById(R.id.btn_search);
        BtnSearch.setOnClickListener(this);
        mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

    }


     //검색버튼 클릭시 적용
    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btn_search:
                hideKeyboard();
                startSearch(EtKeyword.getText().toString());


                break;
        }
    }

    public void hideKeyboard() {
        mInputMethodManager.hideSoftInputFromWindow(RvBooks.getWindowToken(), 0);
    }

    
    //검색창에 아무것도 적성안할때.
    public void showEmptyFieldMessage() {
        Toast.makeText(getContext(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
    }

    //정보가 없을시 
    public void showNotFoundMessage(String keyword) {
        Toast.makeText(getContext(), "\'" + keyword + "\' 를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
    }

    // 검색어가 입력되었는지 확인 후 영화 가져오기
    public void startSearch(String title) {
        if (title.isEmpty()) {
            showEmptyFieldMessage();
        } else {
            mLayoutManager.scrollToPosition(0);
            getBooks(title);
        }
    } //검색어 


    // 레트로핏 책정보를 가져온다
    public void getBooks(final String title) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Book> call = apiInterface.getBooks(title, 100, 1);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                //응답성공시 목록을출력 
                if(response.isSuccessful()) {
                    ArrayList<Item> books = new ArrayList(response.body().getItems());
                    
                    //응답 실패시 제목과 관련된정보가 없을시 찾을수없다고 토스트를띄움 
                    if (books.size() == 0) {
                        bookAdapter.clearItems();
                        showNotFoundMessage(title); 
                    } else {
                        bookAdapter.clearAndAddItems(books);
                    }
                }else{
                }
            }
            
            @Override
            public void onFailure(Call<Book> call, Throwable t) {
            }
        });
    } //getBooks 

}
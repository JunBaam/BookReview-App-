package com.example.bookreview.BottomNavi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bookreview.ActivityResultEvent;
import com.example.bookreview.Adapter.HomeAdpater;
import com.example.bookreview.BusProvider;
import com.example.bookreview.HomeItem;
import com.example.bookreview.Login;
import com.example.bookreview.LoginActivity;
import com.example.bookreview.MainActivity;
import com.example.bookreview.MypageModifyActivity;
import com.example.bookreview.R;
import com.example.bookreview.ReviewItem;
import com.example.bookreview.UserApi;
import com.example.bookreview.UserItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class FragMypage extends Fragment {

    private static final String TAG = "FragMypage ";
    private View view;
    SharedPreferences login_pref;
    Login login;
    private UserApi userApi;
    TextView tv_mypageId ,tv_myBoard;
    Button btn_profile_modify,btn_mypageBoard,btn_bookMark;
    ImageView iv_profile;
    List<UserItem> userItems ;
    List<ReviewItem> reviewItemList;
    String userImage,user;  //프로필 이미지
   Bitmap bitmap;

   String idx;

    private FragMypage fragMypage;

    private final String BASEURL = "http://15.165.60.40/user/";
    //프래그먼트는 onCreateView 로 생성한다.
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_mypage ,container , false);

        Log.i(TAG, "onCreateView: ");


        //로그인한 유저의 아이디를 가져온다.
        //플래그먼트에서 shared는 다르게 가져오니 참고할것.
        login_pref = this.getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
         user = login_pref.getString("id","");
        tv_mypageId= view.findViewById(R.id.tv_mypageId);
        tv_mypageId.setText(user);

        //프로필사진
        iv_profile=view.findViewById(R.id.iv_mypage_profile);

        //userImage = login_pref.getString("image","");
        //iv_profile.setImageBitmap(StringToBitmap(userImage));
        //쉐어드에 String 형태로 저장된 비트맵 이미지를 다시 비트맵으로 바꿈
        //Log.i(TAG, "기본유저아이디"+ StringToBitmap(userImage));
        btn_mypageBoard = view.findViewById(R.id.btn_mypage_board); //내가등록한게시물버튼
        btn_bookMark=view.findViewById(R.id.btn_bookMark);   // 북마크목록

//        getPosts2();  //서버 DB로부터 프로필이미지를 가져옴


        //프로필수정버튼
        btn_profile_modify=view.findViewById(R.id.btn_profile_modify);
        btn_profile_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             //   Bundle bundle = new Bundle();

                //bundle.putString("image", idx);
               // FragMypage fragMypage = new FragMypage();
               // fragMypage.setArguments(bundle);

               // getActivity().startActivity(new Intent(getActivity(), MypageModifyActivity.class));


                Intent intent =new Intent(getContext(), MypageModifyActivity.class);
                intent.putExtra("image",idx);
             //  getActivity().startActivityForResult();
               startActivity(intent);


            }
        });

        return  view;
    } //onCreateView




    /* 쉐어드에 저장된 아이디에 따라 서버DB에서 이미지를 가져온다. */
    private void getPosts2() {
        final  String id  = login_pref.getString("id","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserApi.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        UserApi userApi = retrofit.create(UserApi.class);

        Call<String> call = userApi.getUserValue(id);

        Log.i("받아옴", id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            //    Log.i("Responsestring", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        //php에 내용들을 이 로그로 안드로이드에 콘솔에 출력한다.
                        Log.i("받아옴", response.body().toString());
                        String jsonresponse = response.body().toString();


                     //  saveInfo(jsonresponse);

                        try {
                            JSONObject jsonObject = new JSONObject(jsonresponse);
            /*
            제이슨 객체의 status 의 값이 ture면.
            data배열을 가져오고 쉐어드에 저장한다.
             */

                            if (jsonObject.getString("status").equals("true")) {

                                JSONArray dataArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    Log.i("받아옴(제이슨오브젝트)", dataobj.toString());

                                    String name = dataobj.getString("name");
                                    idx = dataobj.getString("picture");
                                    Log.i("받아옴 이름)", name);
                                    Log.i("받아옴 이미지 )", idx);

                                    Glide.with(getContext())
                                            .load(BASEURL + dataobj.getString("picture").trim())
                                            .skipMemoryCache(true)
                                            .into(iv_profile);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("받아옴", "실패");
            }
        });

    }


  //프래그먼트 새로고침
    private void  refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();

    }


    @Override
    public void onDestroyView() {
        BusProvider.getInstance().unregister(this);
        super.onDestroyView();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");

 //     refresh();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onstart: ");
        getPosts2();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: 값을가져옴");

        getPosts2();  //서버 DB로부터 프로필이미지를 가져옴

    }




    public void transactFragment(Fragment fragment, boolean reload) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (reload) {
            getFragmentManager().popBackStack();
        }
        transaction.replace(R.id.main_frame , fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored: ");
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







}//Fragment

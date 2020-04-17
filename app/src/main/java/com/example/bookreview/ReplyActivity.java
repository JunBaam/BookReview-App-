package com.example.bookreview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookreview.Adapter.HomeAdpater;
import com.example.bookreview.Adapter.ReplyAdapter;
import com.example.bookreview.UserInterface.Upload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ReplyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReplyAdapter replyAdapter;
    private List<UserItem> userItemList;

    PreferenceHelper preferenceHelper;
    SharedPreferences login_pref;
    String REGIURL = "http://15.165.60.40/user/";
    ReplyApi replyApi;
    private final String BASEURL = "http://15.165.60.40/user/";

    private static final String TAG = "REPLY";
    TextView tv_user,tv_reply;
    ImageView iv_profile;
    EditText et_reply;
    Button btn_reply;


Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //리사이클러뷰 설정
        recyclerView = findViewById(R.id.rv_reply);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ReplyActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ReplyActivity.this,
                new LinearLayoutManager(ReplyActivity.this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

         tv_user =findViewById(R.id.tv_replyUser);
         tv_reply=findViewById(R.id.tv_user_reply); //유저가 작성한 댓글
         iv_profile=findViewById(R.id.iv_replyProfile);


         //댓글 작성버튼
         btn_reply=findViewById(R.id.btn_reply);
         btn_reply.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 InsertReply(); //서버에 댓글을 등록
                 getReply();   //리뷰를 가져옴

             }
         });
    }//onCreate


    @Override
    protected void onResume() {
        super.onResume();
        getUserProfile();

        getReply();
    }

    //댓글등록
   private void  InsertReply(){

  ReplyApi replyApi = ApiClient.getApiClient().create(ReplyApi.class);

       //edittext에 적은 댓글을 가져옴
       et_reply=findViewById(R.id.et_reply_rb);
       String reply = String.valueOf(et_reply.getText());

       //쉐어드에 사용자,프로필이미지 저장
       login_pref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
       String name = login_pref.getString("id","");
       String image = login_pref.getString("Image","");

       //현재시간 가져오기 년 월 일 시간 분
       long now = System.currentTimeMillis();
       Date date = new Date(now);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
       String getTime = sdf.format(date);

       //isbn
       Intent intent=getIntent();
       String isbn= intent.getStringExtra("isbn");

       //리사이클러뷰에 바뀐걸 adapter에 알림
       replyAdapter.notifyDataSetChanged();

       //서버로 POST전송
      Call<UserItem> call = replyApi.InserReply(image ,name, reply,getTime,isbn);

  call.enqueue(new Callback<UserItem>() {
    @Override
    public void onResponse(Call<UserItem> call, Response<UserItem> response) {

        String value = response.body().getValue();
        String message = response.body().getMassage();

    }

    @Override
    public void onFailure(Call<UserItem> call, Throwable t) {
        Log.i(TAG, "onFailure: ");

    }
});

   }

//    public void getReply2() {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(REGIURL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//
//        ReplyApi replyApi = retrofit.create(ReplyApi.class);
//
//        Intent intent=getIntent();
//        String isbn= intent.getStringExtra("isbn");
//
//        Log.i(TAG, "Reply2: " + isbn);
//        Call<String> call = replyApi.GetReply(isbn);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i("Reply", response.body().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.i("Reply", "실패");
//            }
//        });
//
//
//    }


    public void getReply() {

        ReplyApi replyApi = ApiClient.getApiClient().create(ReplyApi.class);
        Intent intent=getIntent();
        String isbn= intent.getStringExtra("isbn");

        Call<List<UserItem>> call = replyApi.GetReply(isbn);

        call.enqueue(new Callback<List<UserItem>>() {
            @Override
            public void onResponse(Call<List<UserItem>> call, Response<List<UserItem>> response) {

               userItemList=response.body();
                Log.i(TAG, String.valueOf(userItemList));

                //Activity  == ReplyActivity.this 기억할것
                replyAdapter = new ReplyAdapter(ReplyActivity.this, userItemList);
                recyclerView.setAdapter(replyAdapter);
                replyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<UserItem>> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });

    }//getReply





  //유저 이미지를 가져옴
    private void getUserProfile() {

        login_pref = getSharedPreferences("logininfo", Context.MODE_PRIVATE);
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
                                    String profile = dataobj.getString("picture");

                                    Log.i("받아옴 이름)", name);
                                    Log.i("받아옴 이미지ㄹ )", profile);

                                    //쉐어드에 이미지를 저장

                                    SharedPreferences login_pref =getSharedPreferences("logininfo",MODE_PRIVATE);
                                    SharedPreferences.Editor edit = login_pref.edit();
                                    edit.putString("Image", BASEURL+profile);
                                    edit.apply();


//                                    Glide.with(getApplicationContext())
//                                            .load(BASEURL + dataobj.getString("picture").trim())
//                                            .skipMemoryCache(true)
//                                            .into(iv_profile);

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


}//class

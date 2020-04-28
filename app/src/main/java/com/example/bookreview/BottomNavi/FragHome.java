package com.example.bookreview.BottomNavi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookreview.Adapter.HomeAdpater;
import com.example.bookreview.R;
import com.example.bookreview.ReplyActivity;
import com.example.bookreview.ReviewInterface;
import com.example.bookreview.ReviewItem;
import com.example.bookreview.BookReviewModifyAcitivty;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragHome extends Fragment {

    private View view;
    private TextView tv_user;
    private Button btn_remove , btn_modify;

    private static final String TAG = "FragHome액티비티 ";
    SharedPreferences login_pref;
    ReviewInterface reviewInterface;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    private HomeAdpater homeAdpater;
    private  List<ReviewItem> reviewItems;
   HomeAdpater.RecyclerViewClickListener listener;



    //프래그먼트는 onCreateView 로 생성한다.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_home ,container , false);

        //로그인한 유저의 아이디를 가져온다.
        //플래그먼트에서 shared는 다르게 가져오니 참고할것.

        login_pref = this.getActivity().getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        String user = login_pref.getString("id","");

        tv_user= view.findViewById(R.id.tv_user);
        btn_modify =view.findViewById(R.id.home_modify);
        btn_remove= view.findViewById(R.id.home_remove);

       reviewInterface = ApiClient2.getApiClient().create(ReviewInterface.class);

        progressBar = view.findViewById(R.id.progress);

        //리사이클러뷰 설정
        recyclerView = view.findViewById(R.id.rv_home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //클릭리스너
        listener =new HomeAdpater.RecyclerViewClickListener() {

             //댓글이미지뷰 클릭 클릭
            @Override
            public void onReplyClick(View view, int position) {
                Log.i(TAG, "onReplyClick: ");
          Intent intent =new Intent(getContext(), ReplyActivity.class);
          String isbn= reviewItems.get(position).getIsbn();  //책고유번호

                intent.putExtra("isbn",isbn);

                startActivity(intent);

            }



            //삭제버튼클릭
            @Override
            public void onRemoveClick(View view, final int position) {

              //  final String user = reviewItems.get(position).getUser();
                final String review_user= reviewItems.get(position).getUser();
                final String title =reviewItems.get(position).getTitle();

                Log.i(TAG, "onRemoveClick: "+   review_user+"@@@@"+title);

                //삭제 여부 다이얼로그
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage("게시물을 정말 삭제하시겠어요?");
                dialog.setPositiveButton("네" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        deleteData(  review_user,title);

                        //리사이클러뷰에 해당 위치가 삭제됬음을 알린다.
                        reviewItems.remove (position);
                        homeAdpater.notifyItemRemoved (position);
                    }
                });
                dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            
            //수정버튼클릭
            @Override
            public void onModifyClick(View view, int position) {

                final String title =reviewItems.get(position).getTitle();
                final String content = reviewItems.get(position).getContent();
                final String star = reviewItems.get(position).getStar();


                    Intent intent =new Intent(getContext(), BookReviewModifyAcitivty.class);

                     intent.putExtra("title",title);
                     intent.putExtra("content",content);
                     intent.putExtra("star",star);

                    startActivity(intent);



                            }
            //레이아웃클릭
       @Override
       public void onRowClick(View view, int position) {
           Log.i(TAG, "onRowClick: 레이아웃클릭클릭");

    }

    //좋아요 이미지 클릭
            @Override
            public void onLikeClick(View view, int position) {
                final String author = reviewItems.get(position).getAuthor();
               final Boolean like =reviewItems.get(position).getLike();

                Log.i(TAG, "likeclick" + author +"@@@"+like);
                //final Boolean love =reviewItems.get(position).getLove();
                final ImageView iv_like = view.findViewById(R.id.iv_homeLike);

//                if(like){
//                    iv_like.setImageResource(R.drawable.ngood);
//                    reviewItems.get(position).setLike(false);
//                    updateLike(false,author);
//                    homeAdpater.notifyDataSetChanged();
//                }
//                iv_like.setImageResource(R.drawable.good);
//                reviewItems.get(position).setLike(true);
//                updateLike(true,author);
//                homeAdpater.notifyDataSetChanged();
            }

    //북마크 이미지 클릭
    @Override
    public void onLoveClick(View view, final int position) {

        final String author = reviewItems.get(position).getAuthor();
        final Boolean love =reviewItems.get(position).getLove();
        final ImageView iv_love = view.findViewById(R.id.iv_home_love);

        Log.i(TAG, "onLoveClick: "+author+"love::::"+love);


        if(love){
            iv_love.setImageResource(R.drawable.s2);
            reviewItems.get(position).setLove(false);
            updateLove(false,author);
            homeAdpater.notifyDataSetChanged();

            Log.i(TAG, "onLoveClick: "+position+"번눌림");
        }else {
            iv_love.setImageResource(R.drawable.s3);
            reviewItems.get(position).setLove(true);
            updateLove(true,author);
            homeAdpater.notifyDataSetChanged();
        }
    } //onLoveClick


        };

        return  view;
    }//onCreateView

    
    //서버에서 리뷰를 가져온다.
    public void getReview(){
    Call<List<ReviewItem>> call =  reviewInterface.getReview();
    call.enqueue(new Callback<List<ReviewItem>>() {
        @Override
        public void onResponse(Call<List<ReviewItem>> call, Response<List<ReviewItem>> response) {

            progressBar.setVisibility(View.GONE);
            reviewItems=response.body();
             Log.i("@@", String.valueOf(reviewItems));

           homeAdpater = new HomeAdpater(getContext(), reviewItems,listener);
           recyclerView.setAdapter(homeAdpater);
           homeAdpater.notifyDataSetChanged();

        }
        @Override
        public void onFailure(Call<List<ReviewItem>> call, Throwable t) {
          //  Toast.makeText(getContext(), "rp :"+t.getMessage().toString() Toast.LENGTH_SHORT).show();
        }
    });
    }//getReview

     //북마크 업데이트
    public void updateLove(final Boolean love, String author){

        Call<ReviewItem> call =  reviewInterface.update(author, love);
        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                Log.i(TAG, "onResponse: 되긴됨");
                if (value.equals("1")){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReviewItem> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //좋아요 업데이트
    public void updateLike(final Boolean like, String author){

        Call<ReviewItem> call =  reviewInterface.update_like(author, like);
        call.enqueue(new Callback<ReviewItem>() {
            @Override
            public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                Log.i(TAG, "like: 되긴됨");
                if (value.equals("1")){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReviewItem> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }







    //게시물 삭제
    private void deleteData(final String user, final String title) {

        reviewInterface = ApiClient2.getApiClient().create(ReviewInterface.class);

       Call<ReviewItem> call =  reviewInterface.delete_review(user,title);

       call.enqueue(new Callback<ReviewItem>() {
           @Override
           public void onResponse(Call<ReviewItem> call, Response<ReviewItem> response) {

               String value = response.body().getValue();
               String message = response.body().getMassage();

               Log.i("FragHome", response.body().toString());


               if (value.equals("1")){
                   Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
               }

           }

           @Override
           public void onFailure(Call<ReviewItem> call, Throwable t) {
               Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
           }
       });
       
    }
    
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        getReview();
    }


}//homeFrag

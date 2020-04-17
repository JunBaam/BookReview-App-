package com.example.bookreview.Adapter;

import android.content.Context;

import android.content.SharedPreferences;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookreview.BookSearchModel.Item;
import com.example.bookreview.ReviewItem;
import com.example.bookreview.R;
import com.example.bookreview.ReviewItem;

import java.util.ArrayList;
import java.util.List;

public class HomeAdpater extends RecyclerView.Adapter<HomeAdpater.HomeViewHolder> {


    private Context context;
    List<ReviewItem> reviewItemList;
    private RecyclerViewClickListener mListener;
    private static final String TAG = "HomeAdapter";
    private ConstraintLayout constraintLayout;

    private SharedPreferences sharedPreferences ;

    String review_user,shared_user;

   Button btn_remove,btn_modify;

    public HomeAdpater(Context context, List<ReviewItem> reviewItemList,RecyclerViewClickListener listener) {
       this.context = context;
      this.reviewItemList=reviewItemList;
      this.mListener=listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);
        return new HomeViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {

        holder.tv_title.setText(Html.fromHtml(reviewItemList.get(position).getTitle())); //제목
        holder.tv_author.setText(Html.fromHtml(reviewItemList.get(position).getAuthor())); //저자
        holder.tv_content.setText(reviewItemList.get(position).getContent()); //후기내용
        holder.tv_star.setText(reviewItemList.get(position).getStar());//후기점수
        holder.tv_user.setText(reviewItemList.get(position).getUser()+"님의 점수:"); //사용자


        //현재 로그인된 아이디 @@@@@@@@@@@ context 정리해둘것
        sharedPreferences = context.getSharedPreferences("logininfo", Context.MODE_PRIVATE);
        //쉐어드에서 가져오안 아이디  (현재로그인)
         shared_user = sharedPreferences.getString("id","");
        //, 리뷰게시물에서 가져온아이디
        review_user = reviewItemList.get(position).getUser();
        Log.i(TAG, "바인드뷰" + shared_user + review_user);

        //현재 로그인한유저(쉐어드) 와 게시물 (리뷰)에 아이디가 같으면
        //수정 삭제 버튼을 보이게함
        // == 이 아니라 equals 로 해야됬음 .. ㅡㅡ
        if (shared_user.equals(review_user)){
            holder.btn_remove.setVisibility(View.VISIBLE);
            holder.btn_modify.setVisibility(View.VISIBLE);

            Log.i(TAG, "if문 "+ shared_user +"@"+review_user);
        }


           //후기점수를 가져온다.
             float s1 = Float.parseFloat(reviewItemList.get(position).getStar());
             holder.rb_star.setRating(s1);

             //나중에 알아볼것.
        RequestOptions requestOptions = new RequestOptions();
       // requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.ic_person_black_24dp);
        //requestOptions.error(R.drawable.ic_person_black_24dp);

        //별점
        //holder.rb_star.setRating(reviewItemList.get(position).getStar());
        Glide.with(context)
                .load(reviewItemList.get(position).getUser_image())
                .apply(requestOptions)
                .into(holder.iv_home_userImage);

        //책커버이미지
                Glide.with(context)
                .load(reviewItemList.get(position).getImage())
                .into(holder.iv_image);

        final Boolean love = reviewItemList.get(position).getLove();

        if (love){
            holder.iv_home_love.setImageResource(R.drawable.s3);
        } else {
            holder.iv_home_love.setImageResource(R.drawable.s2);
        }




    }//바인드 뷰홀더



    @Override
    public int getItemCount() {
        return reviewItemList.size();
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;
        private ImageView iv_image,iv_home_love ,iv_home_userImage ,iv_reply;//책커버 , 즐겨찾기
        private TextView tv_title,tv_author,tv_user,
                tv_content,tv_likeCount,tv_replyCount,tv_star ;

        private RatingBar rb_star;

        Button btn_remove,btn_modify;

        public HomeViewHolder(View itemView,RecyclerViewClickListener listener) {
            super(itemView);

            iv_home_love=itemView.findViewById(R.id.iv_home_love);
            iv_image=itemView.findViewById(R.id.iv_bookcover);
            iv_reply=itemView.findViewById(R.id.iv_homeReply); //댓글이미지뷰

            tv_title=itemView.findViewById(R.id.tv_home_title);
            tv_author=itemView.findViewById(R.id.tv_home_author);
            tv_user=itemView.findViewById(R.id.tv_homeUserId);
            tv_content=itemView.findViewById(R.id.tv_homeContent);
            tv_likeCount=itemView.findViewById(R.id.tv_likeCount);
            tv_replyCount=itemView.findViewById(R.id.tv_replyCount);
            tv_star=itemView.findViewById(R.id.tv_rating_count);
            rb_star=itemView.findViewById(R.id.rb_home);
            iv_home_userImage=itemView.findViewById(R.id.tv_homeProfile);

            //수정 삭제 버튼
            btn_modify=itemView.findViewById(R.id.home_modify);
            btn_remove=itemView.findViewById(R.id.home_remove);





                //리사이클러뷰레이아웃
            constraintLayout=itemView.findViewById(R.id.homeConst);

            //아이템클릭 리스너
            mListener = listener;
            constraintLayout.setOnClickListener(this);
            iv_home_love.setOnClickListener(this);
            btn_modify.setOnClickListener(this);
            btn_remove.setOnClickListener(this);
            iv_reply.setOnClickListener(this);


        } //뷰홀더 객체


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.homeConst:
                    mListener.onRowClick(constraintLayout, getAdapterPosition());
                    Log.i(TAG, "컨테이너: "+getAdapterPosition()+"번눌림");
                    break;

                case R.id.iv_home_love:
                    mListener.onLoveClick(iv_home_love, getAdapterPosition());
                    Log.i(TAG, "onLoveClick: "+getAdapterPosition()+"번눌림");
                    break;

                case R.id.home_modify:
                    mListener.onModifyClick(btn_modify,getAdapterPosition());
                    Log.i(TAG, "onModifyClick: "+getAdapterPosition()+"번눌림");
                    break;

                case R.id.home_remove:
                    mListener.onRemoveClick(btn_remove,getAdapterPosition());
                    Log.i(TAG, "onRemoveClick: "+getAdapterPosition()+"번눌림");
                    break;

                case R.id.iv_homeReply:
                    mListener.onReplyClick(iv_reply,getAdapterPosition());
                    Log.i(TAG, "onrReplyClick: "+getAdapterPosition()+"번눌림");
                    break;


                default:
                    break;
            }
        }
    }


    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
        void onLoveClick(View view, int position);
        void onModifyClick(View view, int position);
        void onRemoveClick(View view, int position);
        void onReplyClick(View view, int position);

    }



}//홈어댑터


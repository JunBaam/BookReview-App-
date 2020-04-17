package com.example.bookreview.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookreview.R;
import com.example.bookreview.ReplyActivity;
import com.example.bookreview.ReviewItem;
import com.example.bookreview.UserItem;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {


    private Context context;
    List<UserItem> userItemList;

   //리스너는 잠시만.
    public ReplyAdapter(Context context,  List<UserItem> userItemList){
        this.context = context;
        this.userItemList=userItemList;
    }

    @NonNull
    @Override
    public ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item,parent,false);

        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyViewHolder holder, int position) {


        holder.tv_user.setText(userItemList.get(position).getUser());
        holder.tv_user_reply.setText(userItemList.get(position).getReply());
        holder.tv_date.setText(userItemList.get(position).gettime());


        //댓글 isbn
        String reply_isbn = userItemList.get(position).getimage();
        //리뷰의 isbn
        Intent intent = ((ReplyActivity) context).getIntent();
        String isbn= intent.getStringExtra("isbn");

        Log.i("ReplyAdpater", isbn);

        RequestOptions requestOptions = new RequestOptions();
        // requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.ic_person_black_24dp);

        Glide.with(context)
                .load(userItemList.get(position).getimage())
                .apply(requestOptions)
                .into(holder.iv_reply_profile);


    }  // 바인드뷰홀더

    @Override
    public int getItemCount() { return userItemList.size(); }


    public class ReplyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_reply_profile;
        TextView tv_user_reply, tv_user ,tv_date;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_reply_profile=itemView.findViewById(R.id.iv_replyProfile);
            tv_user_reply=itemView.findViewById(R.id.tv_user_reply);
            tv_user=itemView.findViewById(R.id.tv_replyUser);
          tv_date=itemView.findViewById(R.id.tv_date);


        }//답글 뷰홀더 생성자
    }//답글 뷰홀더 class


}//ReplyAdpater class

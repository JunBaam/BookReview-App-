package com.example.bookreview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreview.R;
import com.example.bookreview.ReviewItem;
import com.example.bookreview.UserItem;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {

    private Context context;
    List<UserItem> userItemList;

   //리스너는 잠시만.
    public ReplyAdapter(Context content,  List<UserItem> userItemList){
        this.context = context;
        this.userItemList=userItemList;
    }

    @NonNull
    @Override
    public ReplyAdapter.ReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_item,parent,false);

        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ReplyViewHolder holder, int position) {

        holder.tv_user.setText(userItemList.get(position).getName());
        holder.tv_user_reply.setText(userItemList.get(position).getReply());

    }  // 바인드뷰홀더

    @Override
    public int getItemCount() { return userItemList.size(); }


    public class ReplyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_reply_profile;
        TextView tv_user_reply, tv_user;

        public ReplyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_reply_profile=itemView.findViewById(R.id.iv_replyProfile);
            tv_user_reply=itemView.findViewById(R.id.tv_user_reply);
            tv_user=itemView.findViewById(R.id.tv_user);



        }//답글 뷰홀더 생성자
    }//답글 뷰홀더 class


}//ReplyAdpater class

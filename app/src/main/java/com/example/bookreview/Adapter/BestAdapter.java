package com.example.bookreview.Adapter;

import android.app.Activity;
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
import com.bumptech.glide.request.target.Target;
import com.example.bookreview.BestItem;
import com.example.bookreview.BestSeller.BestReviewActivity;
import com.example.bookreview.BestSeller.GetBest;
import com.example.bookreview.BookInfoActivity;
import com.example.bookreview.BookSearchModel.Item;
import com.example.bookreview.R;

import java.util.ArrayList;

public class BestAdapter  extends RecyclerView.Adapter<BestAdapter.CustomViewHolder> {

    private ArrayList<GetBest> bestArrayList =null;
    private Context context= null;
    private static final String TAG = "책정보확인";


    public BestAdapter(Context context,ArrayList<GetBest> list){
        this.context=context;
        this.bestArrayList=list;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView id,title,author,url,price;
        ImageView image;

        CustomViewHolder(View itemView) {
            super(itemView);
          //  this.id= itemView.findViewById(R.id.tv_best_id);
            this.title= itemView.findViewById(R.id.tv_best_title);
            this.author= itemView.findViewById(R.id.tv_best_author);
            this.image= itemView.findViewById(R.id.iv_best_image);
          //  this.url= itemView.findViewById(R.id.tv_best_url);
            this.price= itemView.findViewById(R.id.tv_best_price);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        } //뷰홀더객체


        public ImageView getImage() {
            return image;
        }

    }

    @NonNull
    @Override
    public BestAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_item,null);

        CustomViewHolder viewHolder =new CustomViewHolder(view);
        return viewHolder;
    }



    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {

        CustomViewHolder viewHolder =holder;
         GetBest getBest=  bestArrayList.get(position);

//        holder.id.setText(bestArrayList.get(position).getId());

        holder.title.setText((position+1)+". "+bestArrayList.get(position).getTitle());
        holder.author.setText(bestArrayList.get(position).getAuthor());
  //      holder.image.setText(bestArrayList.get(position).getImage());
  //      holder.url.setText(bestArrayList.get(position).getUrl());
        holder.price.setText(bestArrayList.get(position).getPrice());

        Glide.with(context)
                .load(getBest.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getImage());


 viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {

        context =view.getContext();
         Intent intent = new Intent(view.getContext(), BestReviewActivity.class);
         intent.putExtra("best_image",bestArrayList.get(position).getImage());
         intent.putExtra("best_title",bestArrayList.get(position).getTitle());
         intent.putExtra("best_author",bestArrayList.get(position).getAuthor());
         intent.putExtra("best_price",bestArrayList.get(position).getPrice());
         // intent.putExtra("book_link",mBookInfoArrayList.get(position).getLink());
         // intent.putExtra("book_isbn",mBookInfoArrayList.get(position).getIsbn());

         context.startActivity(intent);
         Log.i(TAG, "onClick:  "+ position);

     }
 }); //뷰홀더 클릭 리스너

    }//바인드뷰홀더

    @Override
    public int getItemCount() {
        return bestArrayList.size();
    }

}

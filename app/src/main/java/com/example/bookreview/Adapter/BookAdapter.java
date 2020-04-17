package com.example.bookreview.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.example.bookreview.BookInfoActivity;
import com.example.bookreview.R;
import com.example.bookreview.BookSearchModel.Item;
import java.util.ArrayList;



public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Item> mBookInfoArrayList;
    private static final String TAG = "책정보확인";

    public BookAdapter(Context context, ArrayList<Item> BookInfoArrayList) {
        mContext = context;
        mBookInfoArrayList = BookInfoArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(v);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BookViewHolder bookViewHolder = (BookViewHolder) holder;


        //제목,저자,가격,출판일
        Item item = mBookInfoArrayList.get(position);
        bookViewHolder.tv_title.setText(Html.fromHtml(item.getTitle()));
        bookViewHolder.tv_author.setText(Html.fromHtml(item.getAuthor()));
        bookViewHolder.tv_price.setText(item.getPrice());
        bookViewHolder.tv_date.setText(item.getPubdate());

        //bookViewHolder.tv_price.setText(Html.fromHtml(item.getPrice()));
        //bookViewHolder.tv_pubdate.setText(Html.fromHtml(item.getPubdate()));

        //글라이드로 이미지를 가져옴
        Glide.with(mContext)
                .load(item.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bookViewHolder.getImage());

        //뷰홀더 클릭 리스너
        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = view.getContext();
                Intent intent = new Intent(view.getContext(), BookInfoActivity.class);

                //이미지 ,링크 ,제목 , 저자 ,가격 ,출판사 ,제조일자 ,내용 ,고유번호(isbn) 9개
                intent.putExtra("book_image",mBookInfoArrayList.get(position).getImage());
                intent.putExtra("book_link",mBookInfoArrayList.get(position).getLink());
                intent.putExtra("book_title",mBookInfoArrayList.get(position).getTitle());
                intent.putExtra("book_author",mBookInfoArrayList.get(position).getAuthor());
                intent.putExtra("book_price",mBookInfoArrayList.get(position).getPrice());
                intent.putExtra("book_publish",mBookInfoArrayList.get(position).getPublisher());
                intent.putExtra("book_date",mBookInfoArrayList.get(position).getPubdate());
                intent.putExtra("book_contents",mBookInfoArrayList.get(position).getDescription());
                intent.putExtra("book_isbn",mBookInfoArrayList.get(position).getIsbn());


                mContext.startActivity(intent);
                Log.i(TAG, "onClick:  "+ position);


            }
        });//뷰홀더 클릭 리스너

    }// onBindViewHolder



    @Override
    public int getItemCount() {
        return mBookInfoArrayList.size();
    }


    public void clearItems() {
        mBookInfoArrayList.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddItems(ArrayList<Item> items) {
        mBookInfoArrayList.clear();
        mBookInfoArrayList.addAll(items);
        notifyDataSetChanged();
    }

     //뷰홀더 작성부분

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        //이미지 ,제목, 저자 ,가격 ,날짜
        private ImageView mIvPoster;
        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_price;
        private TextView tv_date;

        BookViewHolder(View view) {
            super(view);
            mIvPoster = view.findViewById(R.id.iv_poster);
            tv_title = view.findViewById(R.id.tv_title);
            tv_author = view.findViewById(R.id.tv_author);
            tv_price = view.findViewById(R.id.tv_price);
            tv_date = view.findViewById(R.id.tv_pubdate);

                /*
            //아이템클릭이벤트처리
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos =getAdapterPosition(); // 아이템의 위치를 나타낸다.
                    if(pos != RecyclerView.NO_POSITION){
                        // Log.i(TAG, "위치"+pos);
                    }
                }
            }); // setOnClicklistener
                */
        } //뷰홀더 객체

        public ImageView getImage() {
            return mIvPoster;
        }
    }//뷰홀더
}
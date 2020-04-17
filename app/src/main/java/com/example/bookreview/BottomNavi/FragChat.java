package com.example.bookreview.BottomNavi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreview.Adapter.BestAdapter;
import com.example.bookreview.BestSeller.GetBest;
import com.example.bookreview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragChat extends Fragment {
     private  String BASEURL = "http://15.165.60.40/book_review/";
    private static String TAG = "phptest";
     private View view;
     TextView textView;
     private ArrayList<GetBest> bestArrayList ;
   private  BestAdapter bestAdapter;
    private RecyclerView rv_best;
    private String mJsonString;
    ImageView poster;

    //프래그먼트는 onCreateView 로 생성한다.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_chat, container, false);

        rv_best = view.findViewById(R.id.listView_main_list);
        rv_best.setLayoutManager(new LinearLayoutManager(getContext()));


      bestArrayList  =new ArrayList<>();
      bestAdapter = new BestAdapter(getContext() ,bestArrayList);
        rv_best.setAdapter(bestAdapter);



        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                new LinearLayoutManager(getContext()).getOrientation());
        rv_best.addItemDecoration(dividerItemDecoration);

        GetData task = new GetData();
        task.execute(BASEURL + "/getBest.php", "");

        return view;
    }//onCreateView



      //값을 가져온다.
    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait", null, true, true);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

          //  textView.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

          //     textView.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }




    private void showResult(){

        String TAG_JSON="BestSeller";
        String TAG_ID = "id";
        String TAG_Title = "title";
        String TAG_Author ="author";
        String TAG_Image ="image";
        String TAG_Url ="url";
        String TAG_Price ="price";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String title = item.getString(TAG_Title);
                String author = item.getString(TAG_Author);
                String image = item.getString(TAG_Image);
                String url = item.getString(TAG_Url);
                String price = item.getString(TAG_Price);

               GetBest getBest = new GetBest();

                getBest.setId(id);
                getBest.setTitle(title);
                getBest.setAuthor(author);
                getBest.setUrl(url);
                getBest.setPrice(price);

//                poster = view.findViewById(R.id.iv_best_image);
//                Glide.with(this)
//                        .load(image)
//                        .into(poster); //받은 URL을 글라이드를 통해 iv_poster 이미지뷰에 적용//

                        getBest.setImage(image);


                bestArrayList.add(getBest);
                bestAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }






} //fragment


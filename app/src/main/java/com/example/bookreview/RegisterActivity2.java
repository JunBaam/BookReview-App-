//package com.example.bookreview;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.bookreview.UserInterface.Register;
//import com.example.bookreview.UserInterface.RegisterItem;
//import com.example.bookreview.UserInterface.Upload;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Calendar;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
//
//
//public class RegisterActivity2 extends AppCompatActivity {
//
//    private EditText et_email, et_name ,et_password;
//    private TextView tv_login;
//    private Button btn_register;
//    private PreferenceHelper preferenceHelper;
//    private ProgressDialog progressDialog;
//    private CircleImageView iv_profile;       //프로필 이미지뷰
//    private FloatingActionButton btn_gallery; //갤러리 버튼
//   Upload upload;
//    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
//    private static final int CAPTURE_REQUEST_CODE = 0;
//    private static final int SELECT_REQUEST_CODE = 1;
//
//    Uri ImageUri;
//
//
//    Bitmap bitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register);
//
//        ActionBar actionBar = getSupportActionBar(); // 상단액션바 표시 유무
//        actionBar.hide(); //상단액션바를 숨김
//
//        preferenceHelper = new PreferenceHelper(this);
//
//
//        //회원가입시. LoginActivity로 이동
//        if(preferenceHelper.getIsLogin()){
//            Intent intent = new Intent(RegisterActivity2.this,LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            this.finish();
//        }
//        //레트로핏선언
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl("http://15.165.60.40/user/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//       upload = retrofit.create(Upload.class);
//        progressDialog = new ProgressDialog(RegisterActivity2.this);
//        progressDialog.setMessage("Image Upload....");
//
//
//        et_name =findViewById(R.id.et_name);
//        et_email =findViewById(R.id.et_email);
//        et_password =findViewById(R.id.et_password);
//        iv_profile =findViewById(R.id.iv_profile);
//
//        //프로필 이미지 선택 버튼
//        btn_gallery=findViewById(R.id.btn_gallery);
//        btn_gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(CheckPermission()) {
//                    Intent select = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(select, SELECT_REQUEST_CODE);
//                }
//
//
//            }
//        });  //btn_gallery 종료
//
//        //로그인하기 버튼
//        tv_login =findViewById(R.id.tv_login);
//        tv_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity2.this,LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
//               finish();
//            }
//        });
//
//        //회원가입 버튼클릭시
//        //개인정보등록 , 이미지를 서버에올림
//        btn_register =findViewById(R.id.btn_register);
//        btn_register.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//              // registerMe();
//               progressDialog.show();
//              // ImageUpload(bitmap);
//
//           }
//       }); //btn_register 종료
//
//    }// onCreate 종료
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//
//            case SELECT_REQUEST_CODE:
//            {
//                if(resultCode == RESULT_OK){
//
//                    try {
//                         ImageUri = data.getData();
//                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), ImageUri);
//                        iv_profile.setImageBitmap(bitmap);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//            break;
//        }
//
//    }
//
//    //회원가입메서드 registerMe
//    private void registerMe(){
//        final String id = et_name.getText().toString();
//        final String email = et_email.getText().toString();
//        final String password = et_password.getText().toString();
//
//        String picture = null;
//        if (bitmap == null) {
//            picture = "";
//        } else {
//            picture = getStringImage(bitmap);
//        }
//
//
//
//
//
//        //레트로핏시작.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Register.REGIURL)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//
//        Register api = retrofit.create(Register.class);
//
//        Call<String> call = api.getUserRegi(id,email,password);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            //통신 성공시.
//            //응답 내용 (body)를 받아온다 .
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i("회원정보 응답내용", response.body().toString());
//
//                if (response.isSuccessful()) {
//                    if (response.body() != null) {
//
//                      Log.i("onSuccess", response.body().toString());
//                        String jsonresponse = response.body().toString();
//                        try {
//                            //회원가입정보 파싱데이터
//                            parseRegData(jsonresponse);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        Log.i("onEmptyResponse", "값이 없습니다.");
//                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//
//            //통신 실패시.
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//            }
//        });  //enqueue 종료
//
//    }//registerMe 종료
//
//      /*회원가입정보 파싱데이터 메서드
//        회원가입 성공시 LoginActivity로 넘어감
//       */
//
//    private void parseRegData(String response) throws JSONException {
//        JSONObject jsonObject = new JSONObject(response);
//        if (jsonObject.optString("status").equals("true")){
//            saveInfo(response);
//
//            Toast.makeText(RegisterActivity2.this, "회원가입 완료!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(RegisterActivity2.this,LoginActivity.class);
//
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            this.finish();
//
//        }else {
//            Toast.makeText(RegisterActivity2.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }
//
//    //저장정보 메서드 putIsLogin을 true로 바꾼다.
//    private void saveInfo(String response){
//
//        preferenceHelper.putIsLogin(true);
//
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            if (jsonObject.getString("status").equals("true")) {
//                JSONArray dataArray = jsonObject.getJSONArray("data");
//
//
//                for (int i = 0; i < dataArray.length(); i++) {
//                    JSONObject dataobj = dataArray.getJSONObject(i);
//                    preferenceHelper.putName(dataobj.getString("name"));
//
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//   //이미지 업로드 메서드
//    private void ImageUpload(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
//        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
//        String  img_name = String.valueOf(Calendar.getInstance().getTimeInMillis());
//        Call<RegisterItem> call = upload.uploadImage( img_name,image);
//
//        call.enqueue(new Callback<RegisterItem>() {
//            @Override
//            public void onResponse(Call<RegisterItem> call, Response<RegisterItem> response) {
//                Toast.makeText(RegisterActivity2.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<RegisterItem> call, Throwable t) {
//                Toast.makeText(RegisterActivity2.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//
//            }
//        });
//    }
//
//
//    //권한허가
//    public boolean CheckPermission() {
//        if (ContextCompat.checkSelfPermission(RegisterActivity2.this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RegisterActivity2.this,
//                Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(RegisterActivity2.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity2.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity2.this,
//                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity2.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                new AlertDialog.Builder(RegisterActivity2.this)
//                        .setTitle("Permission")
//                        .setMessage("Please accept the permissions")
//                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //Prompt the user once explanation has been shown
//                                ActivityCompat.requestPermissions(RegisterActivity2.this,
//                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                        MY_PERMISSIONS_REQUEST_LOCATION);
//
//
//                                startActivity(new Intent(RegisterActivity2
//                                        .this,RegisterActivity2.class));
//                                RegisterActivity2.this.overridePendingTransition(0, 0);
//                            }
//                        })
//                        .create()
//                        .show();
//
//
//            } else {
//                ActivityCompat.requestPermissions(RegisterActivity2.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_LOCATION);
//            }
//
//            return false;
//        } else {
//
//            return true;
//
//        }
//    }
//
//
//}

package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.Request.ChangePasswordRequest;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.Model.User;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserID;
    private TextView tvUserFullName;
    private TextView tvUserEmail;
    private EditText etCurrent, etNew;
    private ImageView imgUser;
    private ConstraintLayout btnLogout, bntChangePassword;

    private User user;

    private String accessToken,authorization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        accessToken = SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
        authorization = "Bearer " + accessToken;

        tvUserFullName = findViewById(R.id.tvUserFullName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        imgUser = findViewById(R.id.imgUser);
        btnLogout = findViewById(R.id.btnLogout);
        bntChangePassword = findViewById(R.id.btnChangePassword);
        etCurrent = findViewById(R.id.etCurrentPass);
        etNew = findViewById(R.id.etNewPass);

        getUser();
        bntChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });


        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ProfileActivity.this, UploadImageActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(ProfileActivity.this).logout();
                startActivity(new Intent(ProfileActivity.this, IntroActivity.class));
            }
        });

    }

    private void getUser(){
        APIService apiService = RetrofitClient.getInstant2();
        apiService.getUser(authorization).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    tvUserFullName.setText(user.getFirstName() + " " + user.getLastName());
                    tvUserEmail.setText(user.getEmail());

                } else {
                    Toast.makeText(ProfileActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ChangePassword(){
        APIService apiService = RetrofitClient.getInstant2();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(etCurrent.getText().toString(), etNew.getText().toString());
        apiService.changePassword(authorization, changePasswordRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String message = response.body().string();
                        // Hiển thị thông báo thành công
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Hiển thị thông báo thất bại
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Hiển thị thông báo thất bại
                Toast.makeText(getApplicationContext(), "Đổi mật khẩu thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
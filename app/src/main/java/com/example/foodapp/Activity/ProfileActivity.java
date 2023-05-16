package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.Model.User;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserID;
    private TextView tvUserFullName;
    private TextView tvUserEmail;
    private TextView tvUserName;
    private TextView tvUserGender;
    private ImageView imgUser;
    private ConstraintLayout btnLogout;

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

        getUser();


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
}
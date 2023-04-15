package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.User;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.io.IOException;
import java.net.URL;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUserID;
    private TextView tvUserFullName;
    private TextView tvUserEmail;
    private TextView tvUserName;
    private TextView tvUserGender;
    private ImageView imgUser;
    private ConstraintLayout btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUserID = findViewById(R.id.tvUserID);
        tvUserFullName = findViewById(R.id.tvUserFullName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserGender = findViewById(R.id.tvUserGender);
        imgUser = findViewById(R.id.imgUser);
        btnLogout = findViewById(R.id.btnLogout);

        User user = SharedPrefManager.getInstance(this).getUser();

        tvUserID.setText(String.valueOf(user.getId()));
        tvUserFullName.setText(user.getFname());
        tvUserEmail.setText(user.getEmail());
        tvUserName.setText(user.getUsername());
        tvUserGender.setText(user.getGender());
        Glide.with(this).load(user.getImages()).into(imgUser);

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, UploadImageActivity.class));
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
}
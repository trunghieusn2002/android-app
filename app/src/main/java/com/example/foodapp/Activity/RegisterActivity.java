package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.Request.RegisterRequest;
import com.example.foodapp.API.Response.AuthResponse;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView etFName, etLName, etEmail, etPassword;
    APIService apiService;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFName = findViewById(R.id.etLName);
        etLName = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etPasswod);
        etEmail = findViewById(R.id.etEmail);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signup();
            }
        });
    }

    private void Signup(){
        if (TextUtils.isEmpty(etFName.getText().toString())) {
            etFName.setError("Please enter your First Name");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etLName.getText().toString())) {
            etLName.setError("Please enter your Laset Name");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Please enter your Email");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Please enter your Password");
            etPassword.requestFocus();
            return;
        }

        apiService = RetrofitClient.getInstant2();
        RegisterRequest registerRequest = new RegisterRequest(etFName.getText().toString(), etLName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
        apiService.register(registerRequest)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()) {
                            try {
                                AuthResponse response1 = response.body();
                                if (response1 != null && response1.getAccess_token() != null) {
                                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    SharedPrefManager.getInstance(getApplicationContext()).login(response1.getAccess_token(), response1.getRefresh_token());
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Else", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
package com.example.foodapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Detail;
import com.example.foodapp.Model.Message;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;
import com.example.foodapp.utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvMealName;
    private TextView tvPrice, etDiaChiDetail;
    private ImageView imgMeal;
    private TextView tvInstructions;
    private Button btnAddToCart;
    private int quantity;
    private Detail detail;

    private String accessToken,authorization;

    private Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        accessToken = SharedPrefManager.getInstance(this).getAccessToken();
        authorization = "Bearer " + accessToken;

        int idPost = getIntent().getIntExtra("idPost", -1);
        quantity = 1;

        tvMealName = findViewById(R.id.tvMealName);
        tvPrice = findViewById(R.id.tvPrice);
        etDiaChiDetail = findViewById(R.id.tvDiaChiDetail);
        imgMeal = findViewById(R.id.imgMeal);
        tvInstructions = findViewById(R.id.tvInstructions);
        btnAddToCart = findViewById(R.id.btnAddToCart);


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        getPostDetail(idPost);

    }

    private void getPostDetail(int idPost) {

        APIService apiService = RetrofitClient.getInstant2();
        apiService.getPostDetail(idPost,authorization).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    post = response.body();

                    String imageUrl = "https://" + post.getPostImageDTOs().get(0).getImageDTO().getUrl();
                    tvPrice.setText(post.getPrice()+"$");
                    tvMealName.setText(post.getTitle());
                    Glide.with(DetailActivity.this).load(imageUrl).into(imgMeal);
                    etDiaChiDetail.setText(post.getAddress());
                    tvInstructions.setText(post.getDescription());

                } else {
                    Toast.makeText(DetailActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

}
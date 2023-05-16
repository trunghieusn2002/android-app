package com.example.foodapp.Activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.FavoritePostAdapter;
import com.example.foodapp.Model.Detail;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView tvMealName;
    private TextView tvPrice, etDiaChiDetail;
    private ImageView imgMeal;
    private TextView tvInstructions;
    private Button btnTheoDoi;
    private int quantity;
    private Detail detail;

    private String accessToken,authorization;

    private Post post;
    private List<Post> posts;
    private int isFollowed;


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
        btnTheoDoi = findViewById(R.id.btnAddToCart);
        btnTheoDoi.setVisibility(View.INVISIBLE);


        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TheoDoiBaiDang();
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
                    tvPrice.setText(post.getPrice()+"VNĐ");
                    tvMealName.setText(post.getTitle());
                    Glide.with(DetailActivity.this).load(imageUrl).into(imgMeal);
                    etDiaChiDetail.setText(post.getAddress());
                    tvInstructions.setText(post.getDescription());
                    getPostFollowed();

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
    private void TheoDoiBaiDang(){
        APIService apiService = RetrofitClient.getInstant2();
        apiService.theoDoiPost(authorization, post.getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    if (isFollowed ==1) {
                        btnTheoDoi.setText("Đã Xoá");
                        Toast.makeText(DetailActivity.this, "Huỷ theo dõi thành công: ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnTheoDoi.setText("Đã Thêm");
                        Toast.makeText(DetailActivity.this, "Theo dõi thành công: ", Toast.LENGTH_SHORT).show();
                    }
                    btnTheoDoi.setEnabled(false);

                } else {
                    Toast.makeText(DetailActivity.this, "Thất bại: ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getPostFollowed(){
        APIService apiService = RetrofitClient.getInstant2();
        apiService.getFollowed(authorization).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();
                    for (Post mpost: posts
                         ) {
                        if(mpost.getId() == post.getId())
                        {
                            btnTheoDoi.setText("Huỷ Theo Dõi");
                            btnTheoDoi.setVisibility(View.VISIBLE);
                            isFollowed = 1;
                            return;
                        }
                    }
                    btnTheoDoi.setVisibility(View.VISIBLE);

                } else {
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
            }
        });
    }
}
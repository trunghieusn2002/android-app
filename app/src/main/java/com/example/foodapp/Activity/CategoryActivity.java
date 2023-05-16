package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvProductsByCategory;
    private String accessToken,authorization;

    private List<Post> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        accessToken = SharedPrefManager.getInstance(getApplicationContext()).getAccessToken();
        authorization = "Bearer " + accessToken;

        int idCategory = getIntent().getIntExtra("idCategory", -1);
        Log.d("idCate", "" + idCategory);

        getPostCategory(idCategory);

        rvProductsByCategory = findViewById(R.id.rvProductsByCategory);


    }

    private void getPostCategory(int idCategory){
        APIService apiService = RetrofitClient.getInstant2();
        apiService.getPostCategory(authorization, idCategory).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();

                    PostAdapter mPostAdapter = new PostAdapter(CategoryActivity.this, posts);
                    rvProductsByCategory.setAdapter(mPostAdapter);
                    rvProductsByCategory.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 3));
                    Toast.makeText(CategoryActivity.this, "Thành Công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Thất bại" , Toast.LENGTH_SHORT).show();
                    Log.e("error", ""+ response.message() );
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
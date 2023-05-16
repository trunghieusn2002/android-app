package com.example.foodapp.Activity;

import android.util.Log;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.SearchProductAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView svSearchProduct;
    private RecyclerView rvSearchProducts;
    private SearchProductAdapter searchProductAdapter;
    private List<Post> posts;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        accessToken = SharedPrefManager.getInstance(this).getAccessToken();
        String authorization = "Bearer " + accessToken;

        // Initialize views
        rvSearchProducts = findViewById(R.id.rvSearchProducts); // Replace with your actual RecyclerView ID
        svSearchProduct = findViewById(R.id.svSearchProduct); // Replace with your actual SearchView ID

        // Set up RecyclerView with adapter and layout manager
        APIService apiService = RetrofitClient.getInstant2();
        apiService.getPost(authorization).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();
                    Log.d("post", "" + posts.toString());

                    searchProductAdapter = new SearchProductAdapter(SearchActivity.this, posts);
                    rvSearchProducts.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                    rvSearchProducts.setAdapter(searchProductAdapter);

                    // Set up SearchView listener
                    svSearchProduct.setOnQueryTextListener(SearchActivity.this);
                } else {
                    Toast.makeText(SearchActivity.this, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Faild: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Perform search when user submits query
        searchProductAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Perform search as user types
        searchProductAdapter.getFilter().filter(newText);
        return false;
    }

    private List<Post> getProductList() {
        List<Post> posts = new ArrayList<>();
        // Add products to the list
        return posts;
    }
}
package com.example.foodapp.Activity;

import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.product.ProductAdapter;
import com.example.foodapp.product.Product;
import com.example.foodapp.R;
import com.example.foodapp.category.Category;
import com.example.foodapp.category.CategoryAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Product> products;
    private ImageView imgProfile;
    private RecyclerView rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        RecyclerView rvLastItem;
        rvLastItem = findViewById(R.id.rvLastItem);
        rvCategories = findViewById(R.id.rvMainCategories);

        EditText etMainSearch = findViewById(R.id.etMainSearch);
        etMainSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        APIService apiService = RetrofitClient.getInstant();
        apiService.loadLastProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();

                ProductAdapter mProductAdapter = new ProductAdapter(MainActivity.this, products);
                rvLastItem.setAdapter(mProductAdapter);
                rvLastItem.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        apiService.loadCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this, response.body());
                rvCategories.setAdapter(categoryAdapter);
                rvCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });

    }
}
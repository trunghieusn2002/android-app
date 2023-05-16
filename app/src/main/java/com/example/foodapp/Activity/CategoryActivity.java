package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvProductsByCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        int idCategory = getIntent().getIntExtra("idCategory", -1);

        rvProductsByCategory = findViewById(R.id.rvProductsByCategory);

        APIService apiService = RetrofitClient.getInstant();
//        apiService.loadProductsByCategory(idCategory).enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//
//                ProductByCategoryAdapter productByCategoryAdapter = new ProductByCategoryAdapter(CategoryActivity.this, response.body());
//
//                rvProductsByCategory.setAdapter(productByCategoryAdapter);
//                rvProductsByCategory.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 2));
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//
//            }
//        });

    }
}
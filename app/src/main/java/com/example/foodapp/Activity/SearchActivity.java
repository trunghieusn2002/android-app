package com.example.foodapp.Activity;

import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.R;
import com.example.foodapp.product.Product;
import com.example.foodapp.product.ProductAdapter;
import com.example.foodapp.product.SearchProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView svSearchProduct;
    private RecyclerView rvSearchProducts;
    private SearchProductAdapter searchProductAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize views
        rvSearchProducts = findViewById(R.id.rvSearchProducts); // Replace with your actual RecyclerView ID
        svSearchProduct = findViewById(R.id.svSearchProduct); // Replace with your actual SearchView ID

        // Set up RecyclerView with adapter and layout manager
        APIService apiService = RetrofitClient.getInstant();
        apiService.loadLastProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();

                searchProductAdapter = new SearchProductAdapter(SearchActivity.this, productList);
                rvSearchProducts.setLayoutManager(new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL, false));
                rvSearchProducts.setAdapter(searchProductAdapter);

                // Set up SearchView listener
                svSearchProduct.setOnQueryTextListener(SearchActivity.this);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

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

    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        // Add products to the list
        return productList;
    }
}
package com.example.foodapp.Activity;

import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.product.ProductAdapter;
import com.example.foodapp.product.Product;
import com.example.foodapp.R;
import com.example.foodapp.category.Category;
import com.example.foodapp.category.CategoryAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager2 = findViewById(R.id.view_pager_2);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        MyViewPaperAdapter myViewPaperAdapter = new MyViewPaperAdapter(this);
        mViewPager2.setAdapter(myViewPaperAdapter);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    mViewPager2.setCurrentItem(0);
                } else if(id == R.id.bottom_add){
                    mViewPager2.setCurrentItem(1);
                } else if(id == R.id.bottom_favorite){
                    mViewPager2.setCurrentItem(2);
                } else if(id == R.id.bottom_manage){
                    mViewPager2.setCurrentItem(3);
                }
                    return true;
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_add).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_favorite).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_manage).setChecked(true);
                        break;
                    default:
                        break;
                }
            }
        });

        /*imgProfile = findViewById(R.id.imgProfile);
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
        });*/

    }
}
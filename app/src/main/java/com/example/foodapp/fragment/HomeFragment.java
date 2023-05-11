package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Activity.MainActivity;
import com.example.foodapp.Activity.ProfileActivity;
import com.example.foodapp.Activity.SearchActivity;
import com.example.foodapp.R;
import com.example.foodapp.category.Category;
import com.example.foodapp.category.CategoryAdapter;
import com.example.foodapp.product.Product;
import com.example.foodapp.product.ProductAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View mView;
    private List<Product> products;
    private ImageView imgProfile, imgBanner;
    private RecyclerView rvCategories;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        imgProfile = mView.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        imgBanner = mView.findViewById(R.id.imageView6);
        imgBanner.setImageResource(R.drawable.banner1);

        RecyclerView rvLastItem;
        rvLastItem = mView.findViewById(R.id.rvLastItem);
        rvCategories = mView.findViewById(R.id.rvMainCategories);

        EditText etMainSearch = mView.findViewById(R.id.etMainSearch);
        etMainSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        APIService apiService = RetrofitClient.getInstant();
        apiService.loadLastProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();

                ProductAdapter mProductAdapter = new ProductAdapter(getActivity(), products);
                rvLastItem.setAdapter(mProductAdapter);
                rvLastItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

        apiService.loadCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), response.body());
                rvCategories.setAdapter(categoryAdapter);
                rvCategories.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        return mView;
    }
}

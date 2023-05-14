package com.example.foodapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.R;
import com.example.foodapp.product.Product;
import com.example.foodapp.product.ProductAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageFragment extends Fragment {
    private View mView;
    private List<Product> products;
    private ImageView imgProfile, imgBanner;
    private RecyclerView rvCategories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_manage, container, false);
        APIService apiService = RetrofitClient.getInstant();
        RecyclerView rvLastItem;
        rvLastItem = mView.findViewById(R.id.rvLastItem);
        rvCategories = mView.findViewById(R.id.rvMainCategories);
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
        return mView;
    }
}

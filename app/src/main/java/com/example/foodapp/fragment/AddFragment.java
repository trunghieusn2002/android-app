package com.example.foodapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Model.Category;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {
    private View mView;
    private Spinner mCategorySpinner;
    private ArrayAdapter<String> mCategoryAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add, container, false);

        String accessToken = SharedPrefManager.getInstance(getContext()).getAccessToken();

        // Khởi tạo Spinner và ArrayAdapter
        mCategorySpinner = mView.findViewById(R.id.spCategory);
        mCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);

        APIService apiService = RetrofitClient.getInstant2();
        String authorization = "Bearer " + accessToken;
        apiService.getCategory(authorization).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("API Response", "Categories: " + response);
                if (response.isSuccessful()) {
                    Log.d("API Response", "Categories: " + response);

                    List<Category> categories = response.body();
                    for (Category category: categories
                         ) {
                        mCategoryAdapter.add(category.getName());
                    }

                } else {
                    Log.d("API Response", "Thất bại");
                }

            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


        // Đặt adapter cho Spinner
        mCategorySpinner.setAdapter(mCategoryAdapter);
        return mView;
    }
}

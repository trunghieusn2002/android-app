package com.example.foodapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Activity.ProfileActivity;
import com.example.foodapp.Activity.SearchActivity;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.Model.Category;
import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View mView;
    private List<Post> posts;
    private ImageView imgProfile, imgBanner;
    private RecyclerView rvCategories;
    private String accessToken;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        accessToken = SharedPrefManager.getInstance(getContext()).getAccessToken();
        String authorization = "Bearer " + accessToken;

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

        APIService apiService = RetrofitClient.getInstant2();
        apiService.getPost(authorization).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();

                    PostAdapter mPostAdapter = new PostAdapter(getActivity(), posts);
                    rvLastItem.setAdapter(mPostAdapter);
                    rvLastItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));

                    Toast.makeText(getContext(), "Response successful", Toast.LENGTH_SHORT).show();
                    Log.d("result", "onResponse: "+ response.body());
                } else {
                    Toast.makeText(getContext(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        apiService.getCategory(authorization).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("API Response", "Categories: " + response);
                if (response.isSuccessful()) {
                    Log.d("API Response", "Categories: " + response);

                    CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), response.body());
                    rvCategories.setAdapter(categoryAdapter);
                    rvCategories.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
                } else {
                    Log.d("API Response", "Thất bại");
                }

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        return mView;
    }
}

package com.example.foodapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.ManagePostAdapter;
import com.example.foodapp.Adapter.PostAdapter;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageFragment extends Fragment {
    private View mView;
    private List<Post> posts;
    private ImageView imgProfile, imgBanner;
    private String accessToken,authorization;
    RecyclerView rvPost;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_manage, container, false);
        accessToken = SharedPrefManager.getInstance(getContext()).getAccessToken();
        authorization = "Bearer " + accessToken;

        //ánh xạ
        rvPost = mView.findViewById(R.id.rycManagePost);

        getPost();

        return mView;
    }

    public void getPost(){
        APIService apiService = RetrofitClient.getInstant2();
        apiService.getPostUser(authorization).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    posts = response.body();

                    ManagePostAdapter managePostAdapter = new ManagePostAdapter(getActivity(), posts);
                    rvPost.setAdapter(managePostAdapter);
                    rvPost.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                } else {
                    Toast.makeText(getContext(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getContext(), "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPost();
    }
}

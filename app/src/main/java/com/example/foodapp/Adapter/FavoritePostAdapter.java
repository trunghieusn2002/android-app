package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritePostAdapter extends RecyclerView.Adapter<FavoritePostAdapter.ProductViewHolder> {

    public static final String TAG = "ProductAdapter";

    private List<Post> posts;
    private Context mContext;

    public FavoritePostAdapter(Context context, List<Post> posts) {
        mContext = context;
        this.posts = posts;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_post, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Post post = posts.get(position);


        String imageUrl = "https://" + post.getPostImageDTOs().get(0).getImageDTO().getUrl();
        Log.d("Image URL", imageUrl);

        Glide.with(mContext).load(imageUrl).into(holder.imageViewProduct);
        holder.textViewProductName.setText(post.getTitle());
        holder.imageViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("idPost", post.getId());
                view.getContext().startActivity(intent);
            }
        });
        holder.btnHidePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFollowed(post.getId());
                holder.btnHidePost.setEnabled(false);
                holder.btnHidePost.setText("Đã Xoa");
            }
        });
    }

    private void DeleteFollowed(int id){
        String accessToken = SharedPrefManager.getInstance(mContext).getAccessToken();
        String authorization = "Bearer " + accessToken;
        APIService apiService = RetrofitClient.getInstant2();
        apiService.theoDoiPost(authorization, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Xoá Thành Công: ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Thất bại: ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProduct;
        public TextView textViewProductName;

        public Button btnHidePost;

        public ProductViewHolder(View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            btnHidePost = itemView.findViewById(R.id.buttonDelete);

        }
    }

}

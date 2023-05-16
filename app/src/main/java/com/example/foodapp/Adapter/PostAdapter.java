package com.example.foodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ProductViewHolder> {

    public static final String TAG = "ProductAdapter";

    private List<Post> posts;
    private Context mContext;

    public PostAdapter(Context context, List<Post> posts) {
        mContext = context;
        this.posts = posts;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.last_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Post post = posts.get(position);


        String imageUrl = "https://" + post.getPostImageDTOs().get(0).getImageDTO().getUrl();
        Log.d("Image URL", imageUrl);

        Glide.with(mContext).load(imageUrl).into(holder.productImage);
        holder.postTitile.setText(post.getTitle());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("idPost", post.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView productImage;
        public TextView postTitile;

        public ProductViewHolder(View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            postTitile = itemView.findViewById(R.id.post_title);

        }
    }

}

package com.example.foodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Model.Post;
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> implements Filterable {

    private List<Post> posts; // List of products to display
    private List<Post> postListFiltered; // List of filtered products
    // Listener for search events
    private final Context context;

    // Constructor
    public SearchProductAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        this.postListFiltered = new ArrayList<>(posts);
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // ViewHolder views
        ImageView imgSearchProduct;
        TextView tvSearchProductName;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initialize ViewHolder views
            imgSearchProduct = itemView.findViewById(R.id.imgSearchProduct);
            tvSearchProductName = itemView.findViewById(R.id.tvSearchProductName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout for RecyclerView item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_item, parent, false); // Replace with your actual item layout
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to ViewHolder views
        Post post = postListFiltered.get(position);

        Glide.with(context).load(post.getPostImageDTOs().get(0).getImageDTO().getUrl()).into(holder.imgSearchProduct);
        holder.tvSearchProductName.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return postListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();
                List<Post> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(posts);
                } else {
                    for (Post product : posts) {
                        if (product.getTitle().toLowerCase().contains(query)) {
                            filteredList.add(product);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                postListFiltered.clear();
                postListFiltered.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    // Interface for search events
    public interface SearchListener {
        void onSearchQuery(String query);
        void onSearchClear();
    }
}

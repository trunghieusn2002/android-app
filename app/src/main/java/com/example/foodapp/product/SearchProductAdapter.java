package com.example.foodapp.product;

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
import com.example.foodapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> implements Filterable {

    private List<Product> productList; // List of products to display
    private List<Product> productListFiltered; // List of filtered products
    // Listener for search events
    private final Context context;

    // Constructor
    public SearchProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFiltered = new ArrayList<>(productList);
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
        Product product = productListFiltered.get(position);

        Glide.with(context).load(product.getStrMealThumb()).circleCrop().into(holder.imgSearchProduct);
        holder.tvSearchProductName.setText(product.getStrMeal());
    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString().toLowerCase();
                List<Product> filteredList = new ArrayList<>();

                if (query.isEmpty()) {
                    filteredList.addAll(productList);
                } else {
                    for (Product product : productList) {
                        if (product.getStrMeal().toLowerCase().contains(query)) {
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
                productListFiltered.clear();
                productListFiltered.addAll((List) filterResults.values);
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

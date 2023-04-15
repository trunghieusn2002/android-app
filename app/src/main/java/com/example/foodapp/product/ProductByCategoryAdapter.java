package com.example.foodapp.product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.R;

import java.util.List;

public class ProductByCategoryAdapter extends RecyclerView.Adapter<ProductByCategoryAdapter.ViewHolder> {

    private final List<Product> productList; // List of products
    private final Context context; // Context

    // Constructor
    public ProductByCategoryAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    // ViewHolder class for holding the views

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_by_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        // Set product data to the views
        Glide.with(context).load(product.getStrMealThumb()).circleCrop().into(holder.imgProductByCategory);
        holder.tvProductByCategoryName.setText(product.getStrMeal());

        holder.imgProductByCategory.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra("idMeal", product.getIdMeal());
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductByCategory;
        TextView tvProductByCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            imgProductByCategory = itemView.findViewById(R.id.imgProductByCategory);
            tvProductByCategoryName = itemView.findViewById(R.id.tvProductByCategoryName);
        }
    }
}

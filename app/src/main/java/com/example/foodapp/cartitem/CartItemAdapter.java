package com.example.foodapp.cartitem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodapp.Activity.DetailActivity;
import com.example.foodapp.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private final List<CartItem> cartItemList;
    private final Context context;

    // Constructor to initialize the list of cart items
    public CartItemAdapter(Context context, List<CartItem> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    // ViewHolder class for cart items

    @NotNull
    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for cart item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        // Bind cart item data to views
        CartItem cartItem = cartItemList.get(position);



        Glide.with(context).load(cartItem.getStrMealThumb()).into(holder.imgCartItem);
        holder.tvCartItemName.setText(cartItem.getName());
        holder.tvCartItemPrice.setText(String.format("$ %s", cartItem.getPrice()));
        holder.tvCartItemTotalPrice.setText(String.format("$ %s", cartItem.getPrice() * cartItem.getQuantity()));
        holder.tvCartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);

                holder.tvCartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);

                holder.tvCartItemQuantity.setText(String.valueOf(cartItem.getQuantity()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCartItem;
        TextView tvCartItemName;
        TextView tvCartItemPrice;
        TextView tvCartItemTotalPrice;
        TextView tvCartItemQuantity;
        ImageView btnMinus;
        ImageView btnPlus;

        CartItemViewHolder(View itemView) {
            super(itemView);

            imgCartItem = itemView.findViewById(R.id.imgCartItem);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvCartItemTotalPrice = itemView.findViewById(R.id.tvCartItemTotalPrice);
            tvCartItemQuantity = itemView.findViewById(R.id.tvCartItemQuantity);
            btnMinus = itemView.findViewById(R.id.btnCartItemMinus);
            btnPlus = itemView.findViewById(R.id.btnCartItemPlus);
        }
    }
}

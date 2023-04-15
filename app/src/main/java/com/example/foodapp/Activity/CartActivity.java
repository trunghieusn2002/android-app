package com.example.foodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodapp.R;
import com.example.foodapp.cartitem.CartItem;
import com.example.foodapp.cartitem.CartItemAdapter;
import com.example.foodapp.cartitem.CartItemDao;
import com.example.foodapp.cartitem.CartItemDatabase;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCart;
    //private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);

        loadCartItems();

    }

    private void loadCartItems() {
        CartItemDatabase cartItemDatabase = CartItemDatabase.getDatabase(this);
        CartItemDao cartItemDao = cartItemDatabase.cartItemDao();

        LiveData<List<CartItem>> cartItemsLiveData = cartItemDao.getAllCartItems();
        cartItemsLiveData.observe(this, cartItems -> {
            // Update the UI with the cart items
            // ...
            // Inside the observer for cartItemsLiveData
            // Update the UI with the cart items
            cartItems = cartItemsLiveData.getValue();
            if (cartItems != null && !cartItems.isEmpty()) {

                CartItemAdapter cartItemAdapter = new CartItemAdapter(CartActivity.this, cartItems);
                rvCart.setAdapter(cartItemAdapter);
                rvCart.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            }


        });
    }
}
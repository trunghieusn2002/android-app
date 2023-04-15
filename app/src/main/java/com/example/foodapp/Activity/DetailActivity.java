package com.example.foodapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.room.Room;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Model.Detail;
import com.example.foodapp.Model.Message;
import com.example.foodapp.R;
import com.example.foodapp.cartitem.CartItem;
import com.example.foodapp.cartitem.CartItemDao;
import com.example.foodapp.cartitem.CartItemDatabase;
import com.example.foodapp.utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvMealName;
    private TextView tvPrice;
    private ImageView imgMeal;
    private TextView tvInstructions;
    private EditText etQuantity;
    private ImageView btnMinus;
    private ImageView btnPlus;
    private Button btnAddToCart;
    private int quantity;
    private Detail detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int idMeal = getIntent().getIntExtra("idMeal", -1);
        quantity = 1;

        tvMealName = findViewById(R.id.tvMealName);
        tvPrice = findViewById(R.id.tvPrice);
        imgMeal = findViewById(R.id.imgMeal);
        tvInstructions = findViewById(R.id.tvInstructions);
        etQuantity = findViewById(R.id.etDetailQuantity);
        btnMinus = findViewById(R.id.btnDetailMinus);
        btnPlus = findViewById(R.id.btnDetailPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.this.setQuantity(quantity - 1);

                etQuantity.setText(String.valueOf(quantity));
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuantity(quantity + 1);

                etQuantity.setText(String.valueOf(quantity));
            }
        });

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String quantityString = charSequence.toString();
                if (!quantityString.isEmpty()) {
                    int quantity = Integer.parseInt(quantityString);
                    if (quantity <= 0) {
                        // Display an error message or take appropriate action
                        etQuantity.setError("Quantity must be greater than 0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CartItemDatabase cartItemDatabase = CartItemDatabase.getDatabase(DetailActivity.this);
                CartItemDao cartItemDao = cartItemDatabase.cartItemDao();


                // Check if the item already exists in the cart
                CartItem existingCartItem = cartItemDao.getCartItemById(detail.getId());
                if (existingCartItem != null) {
                    // If the item already exists, update the quantity
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                    cartItemDao.updateCartItem(existingCartItem);
                } else {
                    // If the item does not exist, insert a new cart item
                    CartItem cartItem = new CartItem();

                    cartItem.setId(detail.getId());
                    cartItem.setName(detail.getMeal());
                    cartItem.setPrice(detail.getPrice());
                    cartItem.setStrMealThumb(detail.getStrmealthumb());
                    cartItem.setQuantity(quantity);

                    cartItemDao.insertCartItem(cartItem);
                }


                startActivity(new Intent(DetailActivity.this, CartActivity.class));

            }
        });

        loadDetail(idMeal);

    }

    private void loadDetail(int idMeal) {

        APIService apiService = RetrofitClient.getInstant();
        apiService.loadDetail(idMeal).enqueue(new Callback<Message<List<Detail>>>() {
            @Override
            public void onResponse(Call<Message<List<Detail>>> call, Response<Message<List<Detail>>> response) {
                detail = response.body().getResult().get(0);

                tvMealName.setText(detail.getMeal());
                tvPrice.setText("$ " + detail.getPrice());
                // Load the image from URL using Glide
                Glide.with(DetailActivity.this)
                        .asBitmap()
                        .load(detail.getStrmealthumb()) // Replace "YOUR_IMAGE_URL" with the actual URL of your image
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                // Convert the loaded image to a circular shape
                                Bitmap circularBitmap = ImageUtils.getCircularBitmap(resource); // Call a helper method to get circular bitmap
                                Drawable circularDrawable = new BitmapDrawable(getResources(), circularBitmap);

                                // Set the circular image as the background of the ImageView
                                imgMeal.setBackground(circularDrawable);
                            }

                            @Override
                            public void onLoadCleared(Drawable placeholder) {
                                // Handle placeholder image if needed
                            }
                        });
                //Glide.with(DetailActivity.this).load(detail.getStrmealthumb()).into(imgMeal);
                tvInstructions.setText(detail.getInstructions());
            }

            @Override
            public void onFailure(Call<Message<List<Detail>>> call, Throwable t) {

            }
        });
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

}
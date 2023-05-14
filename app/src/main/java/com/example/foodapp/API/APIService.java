package com.example.foodapp.API;

import com.example.foodapp.API.Request.LoginRequest;
import com.example.foodapp.API.Request.RegisterRequest;
import com.example.foodapp.API.Response.AuthResponse;
import com.example.foodapp.Model.*;

import com.example.foodapp.Model.Category;
import com.example.foodapp.product.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface APIService {

   @POST("auth/register")
   @Headers("Content-Type: application/json")
   Call<AuthResponse> register(@Body RegisterRequest request);

   @POST("auth/authenticate")
   @Headers("Content-Type: application/json")
   Call<AuthResponse> login(@Body LoginRequest request);

   @GET("category")
   @Headers({"Content-Type: application/json"})
   Call<List<Category>> getCategory(@Header("Authorization") String authorization);



   @GET("lastproduct.php")
   Call<List<Product>> loadLastProduct();

   @POST("newmealdetail.php")
   @FormUrlEncoded
   Call<Message<List<Detail>>> loadDetail(@Field("id") int id);

   @POST("getcategory.php")
   @FormUrlEncoded
   Call<List<Product>> loadProductsByCategory(@Field("idcategory") int id);

}

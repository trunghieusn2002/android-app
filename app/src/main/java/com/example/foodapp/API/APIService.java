package com.example.foodapp.API;

import com.example.foodapp.API.Request.CreatePostRequest;
import com.example.foodapp.API.Request.LoginRequest;
import com.example.foodapp.API.Request.RegisterRequest;
import com.example.foodapp.API.Response.AuthResponse;
import com.example.foodapp.API.Response.ImageResponse;
import com.example.foodapp.API.Response.PostResponse;
import com.example.foodapp.Model.*;

import com.example.foodapp.Model.Category;
import com.example.foodapp.product.Product;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

   @Multipart
   @POST("image/heroku/upload")
   Call<ImageResponse> uploadImage(
           @Header("Authorization") String authorization,
           @Part MultipartBody.Part image
   );

   @POST("post")
   @Headers("Content-Type: application/json")
   Call<PostResponse> createPost(
           @Header("Authorization") String authorization,
           @Body CreatePostRequest request);

   @GET("lastproduct.php")
   Call<List<Product>> loadLastProduct();

   @POST("newmealdetail.php")
   @FormUrlEncoded
   Call<Message<List<Detail>>> loadDetail(@Field("id") int id);

   @POST("getcategory.php")
   @FormUrlEncoded
   Call<List<Product>> loadProductsByCategory(@Field("idcategory") int id);

}

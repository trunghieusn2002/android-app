package com.example.foodapp.API;

import com.example.foodapp.API.Request.CreatePostRequest;
import com.example.foodapp.API.Request.LoginRequest;
import com.example.foodapp.API.Request.RegisterRequest;
import com.example.foodapp.API.Response.AuthResponse;
import com.example.foodapp.API.Response.ImageResponse;
import com.example.foodapp.Model.Post;
import com.example.foodapp.Model.*;

import com.example.foodapp.Model.Category;

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
import retrofit2.http.Query;

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
   Call<Post> createPost(
           @Header("Authorization") String authorization,
           @Body CreatePostRequest request);

   @GET("post")
   @Headers("Content-Type: application/json")
   Call<List<Post>> getPost(@Header("Authorization") String authorization);

   @GET("post/user")
   @Headers("Content-Type: application/json")
   Call<List<Post>> getPostUser(@Header("Authorization") String authorization);

   @GET("post/{postId}")
   @Headers("Content-Type: application/json")
   Call<Post> getPostDetail(@Path("postId") int postId, @Header("Authorization") String authorization);

   @GET("app-user")
   @Headers("Content-Type: application/json")
   Call<User> getUser(@Header("Authorization") String authorization);

   @Headers("Content-Type: application/json")
   @POST("post-follower/{postId}")
   Call<ResponseBody> theoDoiPost(
           @Header("Authorization") String authorization,
           @Path("postId") int postId);

   @POST("post/hide/{id}")
   @Headers("Content-Type: application/json")
   Call<ResponseBody> hidePost(@Path("id") int postId, @Header("Authorization") String authorization);

   @GET("post/followed")
   @Headers("Content-Type: application/json")
   Call<List<Post>> getFollowed(@Header("Authorization") String authorization);

   @GET("post/category")
   @Headers("Content-Type: application/json")
   Call<List<Post>> getPostCategory(@Header("Authorization") String authorization,
                                    @Query("categoryId") int idCategory);


}

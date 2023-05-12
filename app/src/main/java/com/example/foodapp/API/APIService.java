package com.example.foodapp.API;

import com.example.foodapp.Model.*;

import com.example.foodapp.category.Category;
import com.example.foodapp.product.Product;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface APIService {

   @POST("auth/register")
   @FormUrlEncoded
   Call<UserMessage> register(
           @Field("firstName") String firstName,
           @Field("lastName") String lastName,
           @Field("email") String email,
           @Field("password") String password);

   @POST("registrationapi.php?apicall=login")
   @FormUrlEncoded
   Call<UserMessage> login(@Field("username") String username,
                           @Field("password") String password);

   @GET("lastproduct.php")
   Call<List<Product>> loadLastProduct();

   @POST("newmealdetail.php")
   @FormUrlEncoded
   Call<Message<List<Detail>>> loadDetail(@Field("id") int id);

   @GET("categories.php")
   Call<List<Category>> loadCategories();

   @POST("getcategory.php")
   @FormUrlEncoded
   Call<List<Product>> loadProductsByCategory(@Field("idcategory") int id);

}

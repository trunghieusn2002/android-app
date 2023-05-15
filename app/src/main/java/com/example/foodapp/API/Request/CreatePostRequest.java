package com.example.foodapp.API.Request;

import java.util.List;

public class CreatePostRequest {
    private String title;
    private String description;
    private int price;
    private String address;
    private int categoryId;
    private List<Integer> imageIds;

    public CreatePostRequest(String title, String description, int price, String address, int categoryId, List<Integer> imageIds) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.address = address;
        this.categoryId = categoryId;
        this.imageIds = imageIds;
    }

    // Getters and Setters
}


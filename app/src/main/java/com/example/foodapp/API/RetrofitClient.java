package com.example.foodapp.API;

import com.example.foodapp.Model.User;

public class RetrofitClient extends BaseClient {
    private static final String BASE_URL = "http://app.iotstar.vn/appfoods/";
    private static APIService apiService;

    public static APIService getInstant() {
        if (apiService == null)
            return createService(APIService.class, BASE_URL);
        return apiService;
    }
}
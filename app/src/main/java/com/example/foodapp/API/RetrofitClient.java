package com.example.foodapp.API;

public class RetrofitClient extends BaseClient {
    private static final String BASE_URL = "http://app.iotstar.vn/appfoods/";
    private static final String BASE_URL2 = "https://rao-vat-api.herokuapp.com/api/v1/";

    private static APIService apiService;

    public static APIService getInstant() {
        if (apiService == null)
            return createService(APIService.class, BASE_URL);
        return apiService;
    }

    public static APIService getInstant2() {
        if (apiService == null)
            return createService(APIService.class, BASE_URL2);
        return apiService;
    }
}
package com.example.foodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.foodapp.Activity.LoginActivity;
import com.example.foodapp.Model.User;

public class SharedPrefManager {
    // Khởi tạo các hằng key
    private static final String SHARED_PREF_NAME = "retrofitregisterlogin";
    private static final String KEY_FNAME = "keyfname";

    private static final String KEY_LNAME = "keylname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";
    private static final String KEY_IMAGES = "keyimages";
    private static final String KEY_ACCESS_TOKEN = "keyAcessToken";
    private static final String KEY_REFRESH_TOKEN = "keyRefeshToken";

    private static SharedPrefManager mInstance;
    private static Context mContext;

    // Khởi tạo constructor
    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_FNAME, user.getUsername());
        editor.putString(KEY_LNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_IMAGES, user.getImages());
        editor.apply();
    }

    // This method will check whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    // This method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt(KEY_ID, -1),
                "giotocdo",
                "","Nam",
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_IMAGES, null));
    }

    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
    public void login(String accessToken, String refreshToken) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putString(KEY_REFRESH_TOKEN, refreshToken);
        editor.apply();
    }

}

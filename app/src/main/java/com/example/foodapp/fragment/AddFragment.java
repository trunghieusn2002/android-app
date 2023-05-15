package com.example.foodapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.API.APIService;
import com.example.foodapp.API.RetrofitClient;
import com.example.foodapp.Adapter.CategoryAdapter;
import com.example.foodapp.Model.Category;
import com.example.foodapp.Other.RealPathUtil;
import com.example.foodapp.R;
import com.example.foodapp.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {
    private View mView;
    private Spinner mCategorySpinner;
    private ArrayAdapter<String> mCategoryAdapter;
    private ImageView imageView;

    private EditText tvTitile;

    private String imageUrl;

    private Context context;

    private Uri mUri;
    private static final String TAG = AddFragment.class.getName();
    private static final int MY_REQUEST_CODE = 100;
    public static String[] storage_permissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {
            android.Manifest.permission.READ_MEDIA_IMAGES,
            android.Manifest.permission.READ_MEDIA_AUDIO,
            android.Manifest.permission.READ_MEDIA_VIDEO
    };
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.e(TAG, "onActivityResult");
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                            uploadImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add, container, false);

        // ánh xạ
        imageView = mView.findViewById(R.id.imageView);
        tvTitile = mView.findViewById(R.id.tvTitle);

        String accessToken = SharedPrefManager.getInstance(getContext()).getAccessToken();

        // Khởi tạo Spinner và ArrayAdapter
        mCategorySpinner = mView.findViewById(R.id.spCategory);
        mCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);

        APIService apiService = RetrofitClient.getInstant2();
        String authorization = "Bearer " + accessToken;
        apiService.getCategory(authorization).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Log.d("API Response", "Categories: " + response);
                if (response.isSuccessful()) {
                    Log.d("API Response", "Categories: " + response);

                    List<Category> categories = response.body();
                    for (Category category: categories
                         ) {
                        mCategoryAdapter.add(category.getName());
                    }

                } else {
                    Log.d("API Response", "Thất bại");
                }

            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });


        // Đặt adapter cho Spinner
        mCategorySpinner.setAdapter(mCategoryAdapter);

        // Up ảnh

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

        return mView;
    }

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storage_permissions_33;
        } else {
            p = storage_permissions;
        }
        return p;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "2");
            openGallery();
        } else {
            Log.d(TAG, "3");
            requestPermissions(permissions(), MY_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }
    private void uploadImage() {
        // Tạo instance của file từ URI
        String IMAGE_PATH = RealPathUtil.getRealPath(context, mUri);
        Log.e("anh", IMAGE_PATH);
        File file = new File(IMAGE_PATH);

        // Tạo requestFile và partbodyavatar
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        String accessToken = SharedPrefManager.getInstance(getContext()).getAccessToken();
        String authorization = "Bearer " + accessToken;

        APIService apiService = RetrofitClient.getInstant2();
        Call<ResponseBody> call = apiService.uploadImage(authorization, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try{
                        imageUrl = response.body().string();
                        Toast.makeText(context, "Upload image successful", Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }




                } else {
                    Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Failure + " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("anh", t.getMessage());
            }
        });



}

}

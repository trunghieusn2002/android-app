package com.example.foodapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodapp.R;

public class AddFragment extends Fragment {
    private View mView;
    private Spinner mCategorySpinner;
    private ArrayAdapter<String> mCategoryAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add, container, false);

        // Khởi tạo Spinner và ArrayAdapter
        mCategorySpinner = mView.findViewById(R.id.spCategory);
        mCategoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);

        // Thêm dữ liệu vào adapter
        mCategoryAdapter.add("Thể loại 1");
        mCategoryAdapter.add("Thể loại 2");
        mCategoryAdapter.add("Thể loại 3");

        // Đặt adapter cho Spinner
        mCategorySpinner.setAdapter(mCategoryAdapter);
        return mView;
    }
}

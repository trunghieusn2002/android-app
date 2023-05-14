package com.example.foodapp.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodapp.fragment.AddFragment;
import com.example.foodapp.fragment.FavoriteFragment;
import com.example.foodapp.fragment.HomeFragment;
import com.example.foodapp.fragment.ManageFragment;

public class MyViewPaperAdapter extends FragmentStateAdapter {


    public MyViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new AddFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
                return new ManageFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

package com.example.noteapps.onBoardd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapps.databinding.FragmentOnBoardBinding;
import com.example.noteapps.onBoardd.FirstFragment;
import com.example.noteapps.onBoardd.ThereeFragment;

import org.jetbrains.annotations.NotNull;

public class OnBoardFragment extends Fragment {
    FragmentOnBoardBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false);
        initviewpager();

        return binding.getRoot();
    }

    private void initviewpager() {
        if (binding.pager != null){
            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getActivity());
            binding.pager.setAdapter(pagerAdapter);
        }
    }

}
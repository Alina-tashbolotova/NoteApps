package com.example.noteapps.onBoardd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentOnBoardBinding;
import com.example.noteapps.onBoardd.FirstFragment;
import com.example.noteapps.onBoardd.ThereeFragment;
import com.example.noteapps.utils.PreferencesHelper;

import org.jetbrains.annotations.NotNull;

public class OnBoardFragment extends Fragment {
    FragmentOnBoardBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false);
        chabgeonboard();
        initViewpager();
        onboard_view();

        return binding.getRoot();
    }

    private void chabgeonboard() {
        binding.pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.txtSkipOnboard.setVisibility(View.VISIBLE);
                        binding.txtWorkOnboard.setVisibility(View.GONE);
                        break;
                    case 1:
                        binding.txtSkipOnboard.setVisibility(View.VISIBLE);
                        binding.txtWorkOnboard.setVisibility(View.GONE);
                        break;
                    case 2:
                        binding.txtSkipOnboard.setVisibility(View.GONE);
                        binding.txtWorkOnboard.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    private void onboard_view() {
        binding.txtSkipOnboard.setOnClickListener(v -> {
            PreferencesHelper sharedPref = new PreferencesHelper();
            sharedPref.init(requireContext());
            sharedPref.onSaveOnBoardState();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigateUp();

        });
        binding.txtWorkOnboard.setOnClickListener(v -> {
            PreferencesHelper sharedPref = new PreferencesHelper();
            sharedPref.init(requireContext());
            sharedPref.onSaveOnBoardState();
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigateUp();
        });
    }

    private void initViewpager() {
        if (binding.pager != null) {
            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getActivity());
            binding.pager.setAdapter(pagerAdapter);
        }
    }


}
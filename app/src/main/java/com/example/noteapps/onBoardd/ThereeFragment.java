package com.example.noteapps.onBoardd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentOnBoardBinding;
import com.example.noteapps.databinding.FragmentThereeBinding;
import com.example.noteapps.utils.PreferencesHelper;

public class ThereeFragment extends Fragment {
    FragmentThereeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThereeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    
}
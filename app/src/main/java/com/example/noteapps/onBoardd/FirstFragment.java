package com.example.noteapps.onBoardd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentFirstBinding;
import com.example.noteapps.databinding.FragmentHomeBinding;
import com.example.noteapps.databinding.FragmentOnBoardBinding;
import com.example.noteapps.utils.PreferencesHelper;

public class FirstFragment extends Fragment {
    FragmentFirstBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
//        view_skip();
        return binding.getRoot();
    }

//    private void view_skip() {
//        binding.txtSkipFirst.setOnClickListener(v -> {
//            PreferencesHelper sharedPref = new PreferencesHelper();
//            sharedPref.init(requireContext());
//            sharedPref.onSaveOnBoardState();
//            close();
//
//        });
//    }




}
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
import com.example.noteapps.databinding.FragmentSecondBinding;
import com.example.noteapps.utils.PreferencesHelper;

public class SecondFragment extends Fragment {
    FragmentSecondBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        skip_view();
        return binding.getRoot();
    }

    private void skip_view() {
        binding.txtSkipSecond.setOnClickListener(v -> {
            PreferencesHelper sharedPref = new PreferencesHelper();
            sharedPref.init(requireContext());
            sharedPref.onSaveOnBoardState();
            close();
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();
    }
}
package com.example.noteapps.ui.form;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentFormBinding;
import com.example.noteapps.model.TaskModel;
import com.example.noteapps.utils.MyApp;


public class FormFragment extends Fragment {
    FragmentFormBinding binding;
    TaskModel model;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initClickListener();
        return root;
    }

    private void initClickListener() {
        binding.txtReady.setOnClickListener(v -> {
            save();
            navClose();

        });
    }

    private void navClose() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void save() {
        String title2 = binding.etTitle.getText().toString();
        model = new TaskModel(title2);
        MyApp.getInstance().noteDao().insertNote(model);
        Log.e("TAG", "save: ");
        close();
    }

    public void close() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }

}
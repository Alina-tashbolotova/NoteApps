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
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initClickListener();
        initBack();
        return root;
    }

    private void initBack() {
        binding.imageBack.setOnClickListener(v -> {
            close();
        });
    }

    private void initClickListener() {
        binding.txtReady.setOnClickListener(v -> {
            if (binding.etTitle.getText().toString().isEmpty()) {
                binding.etTitle.setError("Enter text");
            } else {
                save();
            }
            close();

        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            model = (TaskModel) getArguments().getSerializable("key");
            if (model != null) {
                binding.etTitle.setText(model.getTitle());
            }
        }
        if (getArguments() != null) {
            TaskModel taskModel = (TaskModel) getArguments().getSerializable("key");
            position = getArguments().getInt("pos");
            binding.etTitle.setText(taskModel.getTitle());

        }

    }


    private void save() {
        String title2 = binding.etTitle.getText().toString();
        if (model == null) {
            model = new TaskModel(title2);
            MyApp.getInstance().noteDao().insertNote(model);
            Log.e("TAG", "FormFragment save: " + title2);
        } else {
            model.setTitle(title2);
            MyApp.getInstance().noteDao().update(model);
            Log.e("TAG", "FormFragment save update: " + title2);
        }

        close();
    }

    public void close() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }

}
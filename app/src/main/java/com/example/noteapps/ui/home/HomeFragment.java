package com.example.noteapps.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapps.MainActivity;
import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentHomeBinding;
import com.example.noteapps.model.TaskModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<TaskModel> models = new ArrayList<>();
    FragmentHomeBinding binding;
    HomeAdapter adapter = new HomeAdapter();


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecycler();
        setResultList();
        setupSearch();
        createList();
        return binding.getRoot();
    }

    private void setupSearch() {
        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });
    }

    private void filter(String text) {
        ArrayList<TaskModel> filter = new ArrayList<>();
        for (TaskModel taskModel : models) {
            if (taskModel.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filter.add(taskModel);
            }

        }
        adapter.filterList(filter);

    }

    private void createList() {
        models = new ArrayList<>();
        models.add(new TaskModel("Нужно", "сделать"));
    }


    private void initRecycler() {
        adapter = new HomeAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.addModel(new TaskModel("ФРУКТЫ", "вишня,яблоко,банан,гранат,дыня,арбуз"));
        adapter.addModel(new TaskModel("ОВОЩИ", "картошка,морковь,лук,редис,свекла,тыква"));
        adapter.addModel(new TaskModel("СУХОФРУКТЫ", "орешки,чернослив,финики,курага"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setResultList() {
        getParentFragmentManager().setFragmentResultListener("task", getViewLifecycleOwner(), (requestKey, result) -> {
            TaskModel taskModel = (TaskModel) result.getSerializable("key");
            if (taskModel != null) {
                adapter.addModel(taskModel);

            }

        });
    }


}
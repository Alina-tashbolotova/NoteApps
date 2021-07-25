package com.example.noteapps.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapps.MainActivity;
import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentHomeBinding;
import com.example.noteapps.model.TaskModel;
import com.example.noteapps.utils.MyApp;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<TaskModel> models = new ArrayList<>();
    FragmentHomeBinding binding;
    HomeAdapter adapter = new HomeAdapter();
    public boolean isChange = true;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecycler();
        getNotesFromDB();
        setupSearch();


        return binding.getRoot();
    }

    private void getNotesFromDB() {
        MyApp.getInstance().noteDao().getAll().observe(requireActivity(), new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> taskModels) {
                adapter.setList(taskModels);
                models = taskModels;
            }
        });



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



    private void initRecycler() {
        adapter = new HomeAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_dash) {
            if (isChange) {
                item.setIcon(R.drawable.ic_dashboard24);
                binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                isChange = false;
            } else {
                item.setIcon(R.drawable.ic_baseline_list_24);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                isChange = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}
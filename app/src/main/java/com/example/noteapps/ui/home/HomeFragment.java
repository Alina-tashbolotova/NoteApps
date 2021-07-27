package com.example.noteapps.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapps.MainActivity;
import com.example.noteapps.R;
import com.example.noteapps.databinding.FragmentHomeBinding;
import com.example.noteapps.model.TaskModel;
import com.example.noteapps.utils.MyApp;
import com.example.noteapps.utils.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<TaskModel> models = new ArrayList<>();
    FragmentHomeBinding binding;
    HomeAdapter adapter;
    NavController navController;
    public boolean isChange = true;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new HomeAdapter();
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initRecycler();
        getNotesFromDB();
        upDataNotes();
        deleteNotes();
        setupSearch();
        return binding.getRoot();
    }

    private void upDataNotes() {
        adapter.setOnItemClickListener((position, taskModel) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            bundle.putSerializable("key", taskModel);
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_home_to_formFragment, bundle);
            Log.e("TAG", "HomeFragment upDataNotes: " + taskModel.getTitle());

        });

    }

    private void deleteNotes() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                MyApp.getInstance().noteDao().delete(models.get(viewHolder.getAdapterPosition()));


            }
        }).attachToRecyclerView(binding.recyclerView);
    }


    private void getNotesFromDB() {
        MyApp.getInstance().noteDao().getAll().observe(requireActivity(), new Observer<List<TaskModel>>() {
            @Override
            public void onChanged(List<TaskModel> taskModels) {
                models = taskModels;
                adapter.setList(taskModels);
                Log.e("TAG", "HomeFragment upDataNotes: " + taskModels);
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
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
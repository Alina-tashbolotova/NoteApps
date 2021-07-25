package com.example.noteapps.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapps.R;
import com.example.noteapps.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    List<TaskModel> list = new ArrayList<>();

    public void addModel(TaskModel model) {
        list.add(model);
        notifyDataSetChanged();
    }
    public void setList(List<TaskModel> models){
        list.clear();
        this.list.addAll(models);
        notifyDataSetChanged();
    }


    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.onBind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(ArrayList<TaskModel> filterList) {
        list = filterList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_item);
        }

        public void onBind(TaskModel taskModel) {
            title.setText(taskModel.getTitle());

        }
    }
}

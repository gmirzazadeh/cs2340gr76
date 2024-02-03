package com.example.cs2340gr76.ui.todo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.ui.todo.Model.ToDoModel;
import com.example.cs2340gr76.ui.todo.ToDoFragment;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private ToDoFragment fragment;

    public ToDoAdapter(ToDoFragment fragment) {
        this.fragment = fragment;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount() {
        return todoList.size();
    }

    private boolean toBoolean(int num) {
        return num != 0;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int view) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ViewHolder(View v) {
            super(v);
            task = v.findViewById(R.id.todoCheckbox);
        }
    }
}

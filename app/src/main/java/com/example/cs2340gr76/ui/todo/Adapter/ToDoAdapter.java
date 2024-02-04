package com.example.cs2340gr76.ui.todo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.ui.todo.AddNewTask;
import com.example.cs2340gr76.ui.todo.Model.ToDoModel;
import com.example.cs2340gr76.ui.todo.ToDoFragment;
import com.example.cs2340gr76.ui.todo.Utils.DatabaseHandler;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;
    private ToDoFragment fragment;
    private DatabaseHandler db;

    public ToDoAdapter(DatabaseHandler db, ToDoFragment fragment) {
        this.db= db;
        this.fragment = fragment;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        db.openDatabase();
        final ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                db.updateStatus(item.getId(), 1);
            } else {
                db.updateStatus(item.getId(), 0);
            }
        });
    }

    public int getItemCount() {
        return todoList.size();
    }

    private boolean toBoolean(int num) {
        return num != 0;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyItemChanged(0);
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int view) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_layout, parent, false);
        db.getAllTasks();
        return new ViewHolder(itemView);
    }

    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return this.fragment.getContext();
    }

    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(this.fragment.getParentFragmentManager(), AddNewTask.TAG);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ViewHolder(View v) {
            super(v);
            task = v.findViewById(R.id.todoCheckbox);
        }
    }
}

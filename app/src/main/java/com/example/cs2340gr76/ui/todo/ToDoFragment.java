package com.example.cs2340gr76.ui.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.cs2340gr76.databinding.FragmentTodoBinding;
import com.example.cs2340gr76.ui.todo.Adapter.ToDoAdapter;
import com.example.cs2340gr76.ui.todo.Model.ToDoModel;
import com.example.cs2340gr76.ui.todo.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Collections;
import java.util.List;


public class ToDoFragment extends Fragment implements DialogCloseListener {

    private FragmentTodoBinding binding;
    private RecyclerView tasksRecyclerView;
    private FloatingActionButton fab;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ToDoViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ToDoViewModel.class);

        binding = FragmentTodoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTodo;

        db = new DatabaseHandler(this.getContext());
        db.openDatabase();

        tasksRecyclerView = binding.tasksRecylerView;
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = binding.tasksFab;

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecylcerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyItemChanged(0, taskList.size());

        fab.setOnClickListener(v -> AddNewTask.newInstance().show(getParentFragmentManager(), AddNewTask.TAG));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyItemChanged(0, taskList.size());
    }
}

package com.example.cs2340gr76.ui.assignments;

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


import com.example.cs2340gr76.databinding.FragmentAssignmentsBinding;
import com.example.cs2340gr76.ui.assignments.Adapter.AssignmentsAdapter;
import com.example.cs2340gr76.ui.assignments.Model.AssignmentsModel;
import com.example.cs2340gr76.ui.assignments.Utils.AssignmentsDataHelper;

import com.example.cs2340gr76.ui.todo.DialogCloseListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.List;


public class AssignmentsFragment extends Fragment implements DialogCloseListener {

    private FragmentAssignmentsBinding binding;
    private RecyclerView assignmentsRecyclerView;
    private FloatingActionButton addFab;
    private FloatingActionButton sortFab;
    private FloatingActionButton timeSortFab;
    private AssignmentsAdapter assignmentsAdapter;
    private List<AssignmentsModel> assignments;
    private AssignmentsDataHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AssignmentsViewModel assignmentsViewModel =
                new ViewModelProvider(this).get(AssignmentsViewModel.class);

        binding = FragmentAssignmentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAssignments;

        db = new AssignmentsDataHelper(this.getContext());
        db.openDatabase();

        assignmentsRecyclerView = binding.assignmentsRecylerView;
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assignmentsAdapter = new AssignmentsAdapter(db, this);
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemAssignments(assignmentsAdapter));
        itemTouchHelper.attachToRecyclerView(assignmentsRecyclerView);

        addFab = binding.assignmentsAdd;
        assignments = db.getAllAssignments();
        assignmentsAdapter.setAssignments(assignments);
        assignmentsAdapter.notifyItemChanged(0, assignments.size());

        addFab.setOnClickListener(v -> AddNewAssignment.newInstance().show(getParentFragmentManager(), AddNewAssignment.TAG));

        sortFab = binding.assignmentsSort;
        sortFab.setOnClickListener(v -> {
            assignmentsAdapter.sortAssignments(true);
        });

        timeSortFab = binding.assignmentsSortTime;
        timeSortFab.setOnClickListener(v -> {
            assignmentsAdapter.sortAssignments(false);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        assignments = db.getAllAssignments();
        assignmentsAdapter.setAssignments(assignments);
        assignmentsAdapter.notifyDataSetChanged();
    }
}

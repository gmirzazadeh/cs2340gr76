package com.example.cs2340gr76.ui.exams;

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
import com.example.cs2340gr76.databinding.FragmentExamsBinding;
import com.example.cs2340gr76.ui.assignments.Adapter.AssignmentsAdapter;
import com.example.cs2340gr76.ui.assignments.AddNewAssignment;
import com.example.cs2340gr76.ui.assignments.AssignmentsViewModel;
import com.example.cs2340gr76.ui.assignments.RecyclerItemAssignments;
import com.example.cs2340gr76.ui.assignments.Utils.AssignmentsDataHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class ExamsFragment extends Fragment implements DialogCloseListener {

    private FragmentExamsBinding binding;
    private RecyclerView examsRecyclerView;
    private ExamAdapter examsAdaptor;
    private List<ExamsModel> examsList;
    private FloatingActionButton addFab;
    private FloatingActionButton sortFab;

    private ExamDatabaseHandler db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExamsViewModel assignmentsViewModel =
                new ViewModelProvider(this).get(ExamsViewModel.class);

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExams;

        db = new ExamDatabaseHandler(this.getContext());
        db.openDatabase();

        examsRecyclerView = binding.examsRecyclerView;
        examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        examsAdaptor = new ExamAdapter(db, this);
        examsRecyclerView.setAdapter(examsAdaptor);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ExamRecyclerTouchHelper(examsAdaptor));
        itemTouchHelper.attachToRecyclerView(examsRecyclerView);

        addFab = binding.examsFab;
        examsList = db.getAllExams();
        examsAdaptor.setExams(examsList);
        examsAdaptor.notifyItemChanged(0, examsList.size());

        addFab.setOnClickListener(v -> AddNewExam.newInstance().show(getParentFragmentManager(), AddNewExam.TAG));

        sortFab = binding.examsSortTime;
        sortFab.setOnClickListener(v -> {
            examsAdaptor.sortAssignments(true);
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
        examsList = db.getAllExams();
        examsAdaptor.setExams(examsList);
        examsAdaptor.notifyDataSetChanged();
    }
}
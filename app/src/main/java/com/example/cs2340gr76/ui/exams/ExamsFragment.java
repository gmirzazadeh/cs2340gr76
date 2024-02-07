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


import com.example.cs2340gr76.databinding.FragmentExamsBinding;
import com.example.cs2340gr76.ui.todo.DialogCloseListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class ExamsFragment extends Fragment implements DialogCloseListener {

    private FragmentExamsBinding binding;
    private RecyclerView examsRecyclerView;
    private FloatingActionButton addFab;
    private FloatingActionButton timeSortFab;
    private ExamsAdapter examsAdapter;
    private List<ExamsModel> exams;
    private ExamsDataHelper db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExamsViewModel examsViewModel =

                new ViewModelProvider(this).get(ExamsViewModel.class);

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textExams;

        db = new ExamsDataHelper(this.getContext());


        db.openDatabase();

        examsRecyclerView = binding.examsRecyclerView;
        examsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        examsAdapter = new ExamsAdapter(db, this);
        examsRecyclerView.setAdapter(examsAdapter);



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ExamRecyclerTouchHelper(examsAdapter));
        itemTouchHelper.attachToRecyclerView(examsRecyclerView);

        addFab = binding.examsAdd;
        exams = db.getAllExams();
        examsAdapter.setExams(exams);
        examsAdapter.notifyItemChanged(0, exams.size());

        addFab.setOnClickListener(v -> AddNewExam.newInstance().show(getParentFragmentManager(), AddNewExam.TAG));

        timeSortFab = binding.examsSortTime;
        timeSortFab.setOnClickListener(v -> {
            examsAdapter.sortExams(true);
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        exams = db.getAllExams();
        examsAdapter.setExams(exams);
        examsAdapter.notifyDataSetChanged();

    }
}
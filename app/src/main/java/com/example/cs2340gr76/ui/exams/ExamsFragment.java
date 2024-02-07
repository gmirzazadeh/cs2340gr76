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


//public class ExamsFragment extends Fragment implements DialogCloseListener {
//
//    private FragmentExamsBinding binding;
//    private ExamAdapter examsAdaptor;
//    private List<ExamModel> examsList;
//
//    private ExamDatabaseHandler db;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//        ExamsViewModel dashboardViewModel =
//                new ViewModelProvider(this).get(ExamsViewModel.class);
//
//        binding = FragmentExamsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        final TextView textView = binding.textExams;
//
////        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//
//        db = new ExamDatabaseHandler(this.getActivity());
//        db.openDatabase();
//
//        examsList = new ArrayList<>();
//
//        binding.examsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        examsAdaptor = new ExamAdapter(db, this);
//        binding.examsRecyclerView.setAdapter(examsAdaptor);
//
//
//
//        examsAdaptor.notifyItemChanged(0, examsList.size());
//
//        binding.examsFab.setOnClickListener(v -> AddNewExam.newInstance().show(getParentFragmentManager(), AddNewExam.TAG));
//
//        examsList = db.getAllExams();
//        Collections.reverse(examsList);
//        examsAdaptor.setExams(examsList);
//
////        ExamModel exam = new ExamModel();
////        exam.setName("Exam 1");
////        exam.setLocation("CULC");
////        exam.setDetail("CS Exam");
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(Calendar.YEAR, 2018);
////        calendar.set(Calendar.MONTH, 11);
////        calendar.set(Calendar.DATE, 18);
////        exam.setTime(calendar);
////        exam.setId(5);
////        examsList.add(exam);
////        examsList.add(exam);
////        examsList.add(exam);
////
////        examsAdaptor.setExams(examsList);
//
//        return root;
//    }
//
//
//
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//    @Override
//    public void handleDialogClose(DialogInterface dialog){
//        examsList = db.getAllExams();
//        Collections.reverse(examsList);
//        examsAdaptor.setExams(examsList);
//        examsAdaptor.notifyDataSetChanged();
//    }
//
//}

public class ExamsFragment extends Fragment implements DialogCloseListener {

    private FragmentExamsBinding binding;
    private RecyclerView examsRecyclerView;
    private FloatingActionButton addFab;
    private FloatingActionButton sortFab;
//    private FloatingActionButton timeSortFab;
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

        sortFab = binding.examsSort;
        sortFab.setOnClickListener(v -> {
            examsAdapter.sortExams(true);
        });

//        timeSortFab = binding.examsSortTime;
//        timeSortFab.setOnClickListener(v -> {
//            examsAdapter.sortExams(false);
//        });

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

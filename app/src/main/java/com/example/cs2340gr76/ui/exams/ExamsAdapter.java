package com.example.cs2340gr76.ui.exams;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.databinding.FragmentExamsBinding;
import com.example.cs2340gr76.databinding.NewExamBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ViewHolder> {

    private List<ExamsModel> examList;
    private ExamsDataHelper db;
    private ExamsFragment fragment;

    private FragmentExamsBinding binding;

    public ExamsAdapter(ExamsDataHelper db, ExamsFragment fragment) {
        this.fragment = fragment;
        this.db = db;
        this.examList = db.getAllExams();
        examList.sort(Comparator.comparing(ExamsModel::getDate).thenComparing(ExamsModel::getTime));
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        db.openDatabase();
        ExamsModel item = examList.get(position);
        if (holder.examNameView != null) {
            holder.examNameView.setText(item.getName());
        }
        if (holder.examLocationView != null) {
            holder.examLocationView.setText(item.getLocation());
        }
        if (holder.examDateView != null) {
            holder.examDateView.setText(item.getDate());
        }
        if (holder.examTimeView != null) {
            holder.examTimeView.setText(item.getTime());
        }
//        holder.binding.newExamName.setText(item.getName());
//        holder.binding.newExamLocation.setText(item.getLocation());
//        holder.binding.newExamDetail.setText(item.getDetail());
//        holder.binding.newExamTime.setText(item.getTime());

    }
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("E, MMM dd, yyyy", Locale.US);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String formatTime(String timeStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.US);
            Date date = inputFormat.parse(timeStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm z", Locale.US);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public void setExams(List<ExamsModel> examList) {
        this.examList.clear();
        this.examList.addAll(examList);
        notifyDataSetChanged();
    }
    public Context getContext() {
        return fragment.getContext();
    }


    public void sortExams(boolean sortCourse) {
        if (sortCourse) {
            examList.sort(Comparator.comparing(ExamsModel::getName)
                    .thenComparing(ExamsModel::getDate)
                    .thenComparing(ExamsModel::getTime));
        } else {
            examList.sort(Comparator.comparing(ExamsModel::getDate)
                    .thenComparing(ExamsModel::getTime)
                    .thenComparing(ExamsModel::getName));
        }
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ExamsModel item = examList.get(position);
//        db.deleteExam(item.getId());
        db.deleteExam(item.getId());
        examList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ExamsModel item = examList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("name", item.getName());
        bundle.putString("location", item.getLocation());
        bundle.putString("date", formatDate(item.getDate()));
        bundle.putString("time", formatTime(item.getTime()));
        AddNewExam fragment = new AddNewExam();
        fragment.setArguments(bundle);
        fragment.show(fragment.getParentFragmentManager(), AddNewExam.TAG);
    }

//    public ExamAdapter(DatabaseHandler db, ExamsFragment fragment) {
//        this.db = db;
//        this.fragment = fragment;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_exams, parent, false);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
//        db.openDatabase();
//
//        final ExamModel item = examList.get(position);
//        holder.task.setText(item.toString());
//        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    db.updateStatus(item.getId(), 1);
//                } else {
//                    db.updateStatus(item.getId(), 0);
//                }
//            }
//        });
//    }
//
//    private boolean toBoolean(int n) {
//        return n != 0;
//    }
//
//    @Override
//    public int getItemCount() {
//        return examList.size();
//    }
//
//    public Context getContext() {
//        return fragment;
//    }
//
//    public void setTasks(List<ExamModel> todoList) {
//        this.examList = todoList;
//        notifyDataSetChanged();
//    }
//
//    public void deleteItem(int position) {
//        ExamModel item = examList.get(position);
//        db.deleteExam(item.getId());
//        examList.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void editItem(int position) {
//        ExamModel item = examList.get(position);
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", item.getId());
//        bundle.putString("task", item.toString());
//        AddNewExam fragment = new AddNewExam();
//        fragment.setArguments(bundle);
//        fragment.show(fragment.getSupportFragmentManager(), AddNewExam.TAG);
//    }
//
    public static class ViewHolder extends RecyclerView.ViewHolder {
//    public NewExamBinding binding;

    TextView examNameView;
    TextView examLocationView;
    TextView examTimeView;
    TextView examDateView;
    TextView exam;

        ViewHolder(View view) {
            super(view);
//            exam = view.findViewById(R.id.text_exams);
            examNameView = view.findViewById(R.id.examTextView);
            examLocationView = view.findViewById(R.id.examLocationTextView);
            examTimeView = view.findViewById(R.id.examTimeTextView);
            examDateView = view.findViewById(R.id.examDateTextView);
        }
    }
}
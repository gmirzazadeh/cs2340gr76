package com.example.cs2340gr76.ui.exams;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private List<ExamModel> examList;
    private ExamDatabaseHandler db;
    private ExamsFragment fragment;

    public ExamAdapter(ExamDatabaseHandler db, ExamsFragment fragment) {
        this.fragment = fragment;
        this.db = db;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_exams, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ExamModel item = examList.get(position);
        holder.exam.setText(item.toString());
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public void setExams(List<ExamModel> examList) {
        this.examList = examList;
        notifyDataSetChanged();
    }
    public Context getContext() {
        return fragment.getContext();
    }

    public void setTasks(List<ExamModel> examList) {
        this.examList = examList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ExamModel item = examList.get(position);
        db.deleteExam(item.getId());
        examList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ExamModel item = examList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("name", item.getName());
        bundle.putString("location", item.getLocation());
        bundle.putString("time", item.getTime());
        bundle.putString("detail", item.getDetail());
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
        TextView exam;

        ViewHolder(View view) {
            super(view);
            exam = view.findViewById(R.id.displayExamDetail);
        }
    }
}
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
import com.example.cs2340gr76.ui.assignments.Model.AssignmentsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private List<ExamsModel> examList;
    private ExamDatabaseHandler db;
    private ExamsFragment fragment;

    private FragmentExamsBinding binding;

    public ExamAdapter(ExamDatabaseHandler db, ExamsFragment fragment) {
        this.fragment = fragment;
        this.db = db;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_card, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();
        final ExamsModel item = examList.get(position);
        if (holder.examNameView != null) {
            holder.examNameView.setText(item.getName());
        }
        if (holder.examLocationView != null) {
            holder.examLocationView.setText(item.getLocation());
        }
        if (holder.examDetailView != null) {
            holder.examDetailView.setText(item.getDate());
        }
        if (holder.examTimeView != null) {
            holder.examTimeView.setText(item.getTime());
        }

    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public void setExams(List<ExamsModel> examList) {
        this.examList = examList;
        notifyDataSetChanged();
    }
    public Context getContext() {
        return fragment.getContext();
    }

    public void setTasks(List<ExamsModel> examList) {
        this.examList = examList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ExamsModel item = examList.get(position);
        db.deleteExam(item);
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

    public void sortAssignments(boolean sortCourse) {
        if (sortCourse) {
            examList.sort(Comparator.comparing(ExamsModel::getDate)
                    .thenComparing(ExamsModel::getTime));
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
    public NewExamBinding binding;

    EditText examNameView;
    EditText examLocationView;
    EditText examTimeView;
    EditText examDetailView;
    TextView exam;

        ViewHolder(View view) {
            super(view);
            examNameView = view.findViewById(R.id.newExamName);
            examLocationView = view.findViewById(R.id.newExamLocation);
            examTimeView = view.findViewById(R.id.newExamTime);
            examDetailView = view.findViewById(R.id.newExamDate);
        }
    }
}
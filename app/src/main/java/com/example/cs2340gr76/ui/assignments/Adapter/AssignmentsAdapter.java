package com.example.cs2340gr76.ui.assignments.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.ui.assignments.AddNewAssignment;
import com.example.cs2340gr76.ui.assignments.AssignmentsFragment;
import com.example.cs2340gr76.ui.assignments.Model.AssignmentsModel;
import com.example.cs2340gr76.ui.assignments.Utils.AssignmentsDataHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder> {

    private List<AssignmentsModel> assignments;

    private AssignmentsFragment fragment;
    private AssignmentsDataHelper db;

    public AssignmentsAdapter(AssignmentsDataHelper db, AssignmentsFragment fragment) {
        this.db = db;
        this.fragment = fragment;
        this.assignments = db.getAllAssignments();
        assignments.sort(Comparator.comparing(AssignmentsModel::getDate).thenComparing(AssignmentsModel::getTime));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int view) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_layout, parent, false);
        db.getAllAssignments();
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        AssignmentsModel assignment = assignments.get(position);
        if (holder.titleTextView != null) {
            holder.titleTextView.setText(assignment.getTitle());
        }
        if (holder.classNameTextView != null) {
            holder.classNameTextView.setText(assignment.getClassName());
        }
        if (holder.dateTextView != null) {
            holder.dateTextView.setText(formatDate(assignment.getDate()));
        }
        if (holder.timeTextView != null) {
            holder.timeTextView.setText(assignment.getTime());
        }
    }


    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dateStr);
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int getItemCount() {
        return assignments.size();
    }

    public Context getContext() {
        return this.fragment.getContext();
    }

    public void setAssignments(List<AssignmentsModel> newAssignments) {
        assignments.clear();
        assignments.addAll(newAssignments);
        notifyDataSetChanged();
    }

    public void sortAssignments(boolean sortCourse) {
        if (sortCourse) {
            assignments.sort(Comparator.comparing(AssignmentsModel::getClassName)
                    .thenComparing(AssignmentsModel::getDate)
                    .thenComparing(AssignmentsModel::getTime));
        } else {
            assignments.sort(Comparator.comparing(AssignmentsModel::getDate)
                    .thenComparing(AssignmentsModel::getTime)
                    .thenComparing(AssignmentsModel::getClassName));
        }
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        AssignmentsModel item = assignments.get(position);
        db.deleteAssignment(item);
        assignments.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        AssignmentsModel item = assignments.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("title", item.getTitle());
        bundle.putString("classname", item.getClassName());
        bundle.putString("date", item.getDate());
        bundle.putString("time", item.getTime());
        AddNewAssignment fragment = new AddNewAssignment();
        fragment.setArguments(bundle);
        fragment.show(this.fragment.getParentFragmentManager(), AddNewAssignment.TAG);
        notifyItemChanged(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView classNameTextView;
        TextView dateTextView;
        TextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.assignmentTextView);
            classNameTextView = itemView.findViewById(R.id.courseTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}

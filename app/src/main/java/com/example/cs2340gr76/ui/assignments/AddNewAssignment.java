package com.example.cs2340gr76.ui.assignments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.cs2340gr76.R;
import com.example.cs2340gr76.ui.assignments.Adapter.AssignmentsAdapter;
import com.example.cs2340gr76.ui.assignments.Model.AssignmentsModel;
import com.example.cs2340gr76.ui.assignments.Utils.AssignmentsDataHelper;
import com.example.cs2340gr76.ui.todo.DialogCloseListener;
import com.example.cs2340gr76.ui.todo.Model.ToDoModel;
import com.example.cs2340gr76.ui.todo.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class AddNewAssignment extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private EditText newAssignmentText;
    private EditText newAssignmentCourse;
    private EditText newAssignmentDate;
    private EditText newAssignmentTime;
    private Button newAssignmentSave;
    private AssignmentsDataHelper db;


    public static AddNewAssignment newInstance() { return new AddNewAssignment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_assignment, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newAssignmentText = getView().findViewById(R.id.newAssignmentTxt);
        newAssignmentCourse = getView().findViewById(R.id.newAssignmentCourse);
        newAssignmentDate = getView().findViewById(R.id.newAssignmentDate);
        newAssignmentTime = getView().findViewById(R.id.newAssignmentTime);
        newAssignmentSave = getView().findViewById(R.id.newAssignmentBtn);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String assignmentText = bundle.getString("title");
            newAssignmentText.setText(assignmentText);

            String assignmentCourse = bundle.getString("classname");
            newAssignmentCourse.setText(assignmentCourse);

            String assignmentDate = bundle.getString("date");
            newAssignmentDate.setText(assignmentDate);

            String assignmentTime = bundle.getString("time");
            newAssignmentTime.setText(assignmentTime);

            assert assignmentText != null;
            assert assignmentCourse != null;
            assert assignmentDate != null;
            assert assignmentTime != null;
            if (assignmentText.length() > 0) {
                newAssignmentSave.setTextColor(ContextCompat.getColor(getContext(), R.color.teal_200));
            }
        }

        db = new AssignmentsDataHelper(getActivity());
        db.openDatabase();

        newAssignmentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newAssignmentSave.setEnabled(false);
                    newAssignmentSave.setTextColor(0xd3d3d3);
                } else {
                    newAssignmentSave.setEnabled(true);
                    newAssignmentSave.setTextColor(ContextCompat.getColor(getContext(), R.color.teal_700));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newAssignmentSave.setOnClickListener(v -> {
            String text = newAssignmentText.getText().toString();

            String course = newAssignmentCourse.getText().toString();
            String date = newAssignmentDate.getText().toString();
            String time = newAssignmentTime.getText().toString();

            AssignmentsModel updatedAssignment = new AssignmentsModel(
                    text, date, time, course, null
            );
            if (finalIsUpdate) {
                updatedAssignment.setId(bundle.getInt("id"));

                db.updateAssignment(updatedAssignment);
            } else {

                AssignmentsModel assignment = new AssignmentsModel(null, null, null, null, null);
                assignment.setTitle(text);
                assignment.setClassName(course);
                assignment.setDate(date);
                assignment.setTime(time);
                db.insertAssignment(assignment);

            }


            db.closeDatabase();
            dismiss();
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity fragment = getActivity();
        if (fragment instanceof com.example.cs2340gr76.ui.todo.DialogCloseListener) {
            ((DialogCloseListener) fragment).handleDialogClose(dialog);
        }
    }
}

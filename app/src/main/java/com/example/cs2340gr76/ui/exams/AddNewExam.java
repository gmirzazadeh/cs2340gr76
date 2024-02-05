package com.example.cs2340gr76.ui.exams;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.cs2340gr76.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewExam extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newExamName;
    private EditText newExamLocation;
    private EditText newExamDetail;
    private EditText newExamTime;
    private Button newExamSaveButton;

    private ExamDatabaseHandler db;

    public static AddNewExam newInstance() {
        return new AddNewExam();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_exam, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newExamName = getView().findViewById(R.id.newExamName);
        newExamLocation = getView().findViewById(R.id.newExamLocation);
        newExamTime = getView().findViewById(R.id.newExamTime);
        newExamDetail = getView().findViewById(R.id.newExamDetail);
        newExamSaveButton = getView().findViewById(R.id.saveExamButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String name = bundle.getString("name");
            newExamName.setText(name);
            String location = bundle.getString("location");
            newExamLocation.setText(name);
            String time = bundle.getString("time");
            newExamTime.setText(name);
            String detail = bundle.getString("detail");
            newExamDetail.setText(name);

            assert name != null;
            assert location != null;
            assert time != null;
            assert detail != null;

            if(name.length()>0)
                newExamSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }

        db = new ExamDatabaseHandler(getActivity());
        db.openDatabase();

        newExamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newExamSaveButton.setEnabled(false);
                    newExamSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newExamSaveButton.setEnabled(true);
                    newExamSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newExamSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newExamName.getText().toString();
                String location = newExamName.getText().toString();
                String detail = newExamName.getText().toString();
                if(finalIsUpdate){
                    db.updateName(bundle.getInt("id"), name);
                }
                else {
                    ExamModel exam = new ExamModel();
                    exam.setName(name);
                    exam.setLocation(location);
                    exam.setDetail(detail);
                    db.insertTask(exam);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener)
            ((DialogCloseListener) activity).handleDialogClose(dialog);
    }


}
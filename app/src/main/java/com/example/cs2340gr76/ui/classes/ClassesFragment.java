package com.example.cs2340gr76.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2340gr76.databinding.FragmentClassesBinding;

import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;
    private List<ClassModel> classList = new ArrayList<>();

    public ArrayList<String> scheduledClasses;
    private SharedViewModel sharedViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize views
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        EditText classNameEditText = binding.classNameEditText;
        EditText meetingTimeEditText = binding.editTextTime;
        //EditText meetingDateEditText = binding.meetingDateEditText;
        EditText locationEditText = binding.editTextTextPostalAddress;


        //TextView classListTextView = binding.classListTextView;

        // Add class button click listener
        binding.addClass.setOnClickListener(view -> {
            String className = classNameEditText.getText().toString();
            String meetingTime = meetingTimeEditText.getText().toString();
            //String meetingDate = meetingDateEditText.getText().toString();
            String location = locationEditText.getText().toString();
            List<String> selectedDays = new ArrayList<>();
            String instructor = binding.instructorName.getText().toString();
            if(binding.checkBoxMon.isChecked()) {
                selectedDays.add("Monday");
            }
            if(binding.checkBoxTues.isChecked()) {
                selectedDays.add("Tuesday");
            }
            if(binding.checkBoxWed.isChecked()) {
                selectedDays.add("Wednesday");
            }
            if(binding.checkBoxThurs.isChecked()) {
                selectedDays.add("Thursday");
            }
            if(binding.checkBoxFri.isChecked()) {
                selectedDays.add("Friday");
            }
            // Create a new class object
            ClassModel newClass = new ClassModel(className, meetingTime, location, selectedDays, instructor);
            classList.add(newClass);

            ArrayList<String> classListStrings = new ArrayList<>();
            for (ClassModel classModel : classList) {
                classListStrings.add(classModel.toString());
            }

            sharedViewModel.setScheduledClasses(classListStrings);

            // Display the list of added classes
            displayClassList(binding.classListTextView);
            classNameEditText.setText("");
            meetingTimeEditText.setText("");
            locationEditText.setText("");
            binding.instructorName.setText("");
            binding.checkBoxMon.setChecked(false);
            binding.checkBoxTues.setChecked(false);
            binding.checkBoxWed.setChecked(false);
            binding.checkBoxThurs.setChecked(false);
            binding.checkBoxFri.setChecked(false);

        });

        return root;
    }

    private void displayClassList(TextView classListTextView) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ClassModel classModel : classList) {
            stringBuilder.append(classModel.toString()).append("\n");
        }

        String classListString = stringBuilder.toString();

        // Make sure scheduledClasses is initialized
        if (scheduledClasses == null) {
            scheduledClasses = new ArrayList<>();
        }

        // Add the string representation to scheduledClasses
        scheduledClasses.add(classListString);

        // Update the TextView
        //classListTextView.setText(classListString);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
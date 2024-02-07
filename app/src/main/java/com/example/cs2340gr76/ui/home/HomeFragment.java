package com.example.cs2340gr76.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.MutableLiveData;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs2340gr76.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textView = binding.monthYearTV;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        //final TextView textView = binding.textHome;
        homeViewModel.getCurrentMonthYear().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newMonthYear) {
                // Update the UI based on the new month and year value
                textView.setText(newMonthYear);
                // You can also update your calendar based on the new month and year value
                // updateCalendar(newMonthYear);
                updateCalendar();
            }
        });
        homeViewModel.updateMonthYear();
        Calendar calendar = Calendar.getInstance();
        String currentDate = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(calendar.getTime());
        //binding.textHome.setText(currentDate);

        return root;
    }
    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }

    private void updateCalendar() {
        // Clear any existing views in the calendar layout
        binding.calendarLayout.removeAllViews();

        // Get the start and end dates of the current week
        Calendar currentWeekStart = Calendar.getInstance();
        currentWeekStart.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        currentWeekStart.set(Calendar.HOUR_OF_DAY, 0);
        currentWeekStart.set(Calendar.MINUTE, 0);
        currentWeekStart.set(Calendar.SECOND, 0);

        Calendar currentWeekEnd = (Calendar) currentWeekStart.clone();
        currentWeekEnd.add(Calendar.DAY_OF_WEEK, 6);

        // Populate the list of dates for the current week
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        for (int i = 0; i < 7; i++) {
            dates.add(dateFormat.format(currentWeekStart.getTime()));
            currentWeekStart.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Display the dates in your UI (you may use a RecyclerView, TextViews, or any other UI element)
        for (String date : dates) {
            TextView dateTextView = new TextView(requireContext());
            date = date.substring(0,2);
            dateTextView.setText(date);
            dateTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dateTextView.setMinWidth(155);
            binding.calendarLayout.addView(dateTextView);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
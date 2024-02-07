package com.example.cs2340gr76.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mCurrentMonthYear;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Weekly Schedule");


        mCurrentMonthYear = new MutableLiveData<>();
        updateMonthYear(); // Initial week
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getCurrentMonthYear() {
        return mCurrentMonthYear;
    }
    public void updateMonthYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String monthYear = dateFormat.format(calendar.getTime());
        mCurrentMonthYear.setValue(monthYear);
    }
}
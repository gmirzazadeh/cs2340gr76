package com.example.cs2340gr76.ui.exams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExamsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ExamsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Exams");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
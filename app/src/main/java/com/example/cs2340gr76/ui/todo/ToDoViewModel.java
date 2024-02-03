package com.example.cs2340gr76.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ToDoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("To-Do List");
    }
    public LiveData<String> getText() {
        return mText;
    }
}
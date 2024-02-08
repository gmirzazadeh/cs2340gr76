package com.example.cs2340gr76.ui.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SharedViewModel extends ClassesViewModel {
    private final MutableLiveData<List<String>> scheduledClasses = new MutableLiveData<>();

    public void setScheduledClasses(List<String> classes) {
        scheduledClasses.setValue(classes);
    }

    public LiveData<List<String>> getScheduledClasses() {
        return scheduledClasses;
    }
}
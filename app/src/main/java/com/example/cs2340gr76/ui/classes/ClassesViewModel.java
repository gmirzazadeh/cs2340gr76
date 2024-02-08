package com.example.cs2340gr76.ui.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ClassesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<ClassModel>> classList;

    public ClassesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Class Schedule Information");

        classList = new MutableLiveData<>();
        classList.setValue(new ArrayList<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<ClassModel>> getClassList() {
        return classList;
    }

    public void addClass(ClassModel newClass) {
        List<ClassModel> currentList = classList.getValue();
        if (currentList != null) {
            currentList.add(newClass);
            classList.setValue(currentList);
        }
    }
}
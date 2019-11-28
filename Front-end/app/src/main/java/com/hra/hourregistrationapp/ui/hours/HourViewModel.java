package com.hra.hourregistrationapp.ui.hours;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HourViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HourViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the hour fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
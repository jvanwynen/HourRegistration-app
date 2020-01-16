package com.hra.hourregistrationapp.ui.hours;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HoursViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HoursViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hours fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
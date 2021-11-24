package com.robak.android.quickroll;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

public class ObservableInteger extends ViewModel {
    private final MutableLiveData<Integer> mutableLiveData = new MutableLiveData<>();

    public void setValue(Integer value) {
        mutableLiveData.setValue(value);
    }
    public Integer getValue() {
        return mutableLiveData.getValue();
    }
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Integer> observer) {
        mutableLiveData.observe(owner, observer);
    }
}

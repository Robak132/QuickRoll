package com.robak.android.quickroll.tools;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.robak.android.quickroll.Modifier;

public class ObservableModifier extends ViewModel {
    private final MutableLiveData<Modifier> mutableLiveData = new MutableLiveData<>();

    private void initIfNeeded() {
        if (mutableLiveData.getValue() == null) {
            mutableLiveData.setValue(new Modifier());
        }
    }
    public int getModifier() {
        initIfNeeded();
        return mutableLiveData.getValue().getModifier();
    }
    public void setModifier(int value) {
        initIfNeeded();
        Modifier modifier = mutableLiveData.getValue();
        modifier.setModifier(value);
        mutableLiveData.setValue(modifier);
    }
    public int getSLModifier() {
        initIfNeeded();
        return mutableLiveData.getValue().getSLModifier();
    }
    public void setSLModifier(int value) {
        initIfNeeded();
        Modifier modifier = mutableLiveData.getValue();
        modifier.setSLModifier(value);
        mutableLiveData.setValue(modifier);
    }
    public Modifier getModifierObject() {
        return mutableLiveData.getValue();
    }
    public void setModifierObject(Modifier modifier) {
        mutableLiveData.setValue(modifier);
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Modifier> observer) {
        mutableLiveData.observe(owner, observer);
    }
}


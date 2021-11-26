package com.robak.android.quickroll.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.R;
import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ObservableModifier;

public class ShieldModifiers extends FragmentWithTools {
    private ObservableModifier observableModifier;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shield_modifiers, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireParentFragment()).get(ObservableModifier.class);
    }
}
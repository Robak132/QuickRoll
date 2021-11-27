package com.robak.android.quickroll.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.R;
import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ObservableModifier;

import java.util.concurrent.atomic.AtomicReference;

public class ShieldModifiers extends FragmentWithTools {
    AtomicReference<Integer> enemySize = new AtomicReference<>(3);

    // INIT
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shield_modifiers, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireParentFragment()).get(ObservableModifier.class);

        ConstraintLayout parentLayout = view.findViewById(R.id.size_table);
        addImageViewSeries(parentLayout, "size", 3, 6, false, enemySize);
        setupConstraints(parentLayout);
    }

    // RESUME
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        setImageColorByTag(view, "size" + enemySize.get(), R.color.purple_primary);
    }

    @Override
    protected int getModifier() {
        return 0;
    }
    @Override
    protected int getSLModifier() {
        return (enemySize.get() - 3) * -2;
    }
}
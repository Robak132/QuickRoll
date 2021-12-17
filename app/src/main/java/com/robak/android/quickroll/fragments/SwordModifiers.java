package com.robak.android.quickroll.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.R;
import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ObservableModifier;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SwordModifiers extends FragmentWithTools {
    AtomicReference<Integer> yourSize = new AtomicReference<>(3);
    List<ImageView> yourSizeList;
    AtomicReference<Integer> enemySize = new AtomicReference<>(3);
    List<ImageView> enemySizeList;
    AtomicReference<Integer> numericAdvantage = new AtomicReference<>(0);
    List<ImageView> numericAdvantageList;

    // INIT
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sword_modifiers, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireParentFragment()).get(ObservableModifier.class);

        ConstraintLayout parentLayout = view.findViewById(R.id.your_size_table);
        yourSizeList = addImageViewSeries(parentLayout, "size", 6, false, yourSize);
        setupConstraints(parentLayout);

        parentLayout = view.findViewById(R.id.enemy_size_table);
        enemySizeList = addImageViewSeries(parentLayout, "size", 6, false, enemySize);
        setupConstraints(parentLayout);

        parentLayout = view.findViewById(R.id.numeric_advantage_table);
        numericAdvantageList = addImageViewSeries(parentLayout, "number", 2, false, numericAdvantage);
        setupConstraints(parentLayout);
    }

    // RESUME
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        yourSizeList.get(yourSize.get()).setColorFilter(getActivity().getColor(R.color.purple_primary));
        enemySizeList.get(enemySize.get()).setColorFilter(getActivity().getColor(R.color.purple_primary));
        numericAdvantageList.get(numericAdvantage.get()).setColorFilter(getActivity().getColor(R.color.purple_primary));
    }

    @Override
    protected int getModifier() {
        int enemySizeMod =  enemySize.get() > yourSize.get() ? 10 : 0;
        int numericAdvantageMod =  numericAdvantage.get() * 20;
        return enemySizeMod + numericAdvantageMod;
    }
    @Override
    protected int getSLModifier() {
        return 0;
    }
}
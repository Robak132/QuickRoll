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

public class BowModifiers extends FragmentWithTools {
    AtomicReference<Integer> range = new AtomicReference<>(2);
    AtomicReference<Integer> enemySize = new AtomicReference<>(3);
    AtomicReference<Integer> aim = new AtomicReference<>(0);
    AtomicReference<Integer> group = new AtomicReference<>(0);
    AtomicReference<Integer> fear = new AtomicReference<>(0);
    AtomicReference<Integer> obstacle = new AtomicReference<>(0);

    // INIT
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bow_modifiers, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireParentFragment()).get(ObservableModifier.class);

        ConstraintLayout parentLayout = view.findViewById(R.id.range_table);
        addImageViewSeries(parentLayout, "range", 4, false, range);
        setupConstraints(parentLayout);

        parentLayout = view.findViewById(R.id.size_table);
        addImageViewSeries(parentLayout, "size", 6, false, enemySize);
        setupConstraints(parentLayout);

        parentLayout = view.findViewById(R.id.group_table);
        addImageView(parentLayout, "aim", aim);
        addImageViewSeries(parentLayout, "group", 3, true, group);
        addImageView(parentLayout, "fear", fear);
        setupConstraints(parentLayout);

        parentLayout = view.findViewById(R.id.obstacle_table);
        addImageViewSeries(parentLayout, "obstacle", 3, true, obstacle);
        setupConstraints(parentLayout);
    }

    // RESUME
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        setImageColorByTag(view, "range" + range.get(), R.color.purple_primary);
        setImageColorByTag(view, "size" + enemySize.get(), R.color.purple_primary);
        if (group.get() != 0)    setImageColorByTag(view, "group" + group, R.color.purple_primary);
        if (obstacle.get() != 0) setImageColorByTag(view, "obstacle" + obstacle, R.color.purple_primary);
        if (fear.get() != 0)     setImageColorByTag(view, "fear", R.color.purple_primary);
        if (aim.get() != 0)      setImageColorByTag(view, "aim", R.color.purple_primary);
    }

    @Override
    protected int getModifier() {
        int enemySizeMod = enemySize.get() - 3;
        enemySizeMod = enemySizeMod < 0 ? enemySizeMod * 10 : enemySizeMod * 20;
        int rangeMod = 2 - range.get();
        rangeMod = rangeMod < 0 ? rangeMod * 10 : rangeMod * 20;
        int obstacleMod = obstacle.get() * (-10);
        int groupMod = group.get() * 20;
        int aimMod = aim.get() * (-20);

        int totalModifier = enemySizeMod + obstacleMod + rangeMod + groupMod + aimMod;
        return limitToRange(totalModifier, -30, 60);
    }
    @Override
    protected int getSLModifier() {
        return fear.get() == 0 ? 0 : -1;
    }
}
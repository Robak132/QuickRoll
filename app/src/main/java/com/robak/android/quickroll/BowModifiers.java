package com.robak.android.quickroll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class BowModifiers extends Fragment {
    MainActivity parent;
    View view;
    private ObservableInteger viewModel;

    int range = 2;
    int enemySize = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bow_modifiers, container, false);
        parent = (MainActivity) getActivity();

        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ObservableInteger.class);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            parent.getImageViewByName("range" + i).setOnClickListener(v -> {
                if (range != finalI) {
                    parent.setImageColor("range" + range, R.color.black);
                    range = finalI;
                    parent.setImageColor("range" + range, R.color.purple_primary);
                    setModifier();
                }
            });
        }
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            parent.getImageViewByName("size" + i).setOnClickListener(v -> {
                if (enemySize != finalI) {
                    parent.setImageColor("size" + enemySize, R.color.black);
                    enemySize = finalI;
                    parent.setImageColor("size" + enemySize, R.color.purple_primary);
                    setModifier();
                }
            });
        }
    }

    void setModifier() {
        int relativeEnemySize = enemySize - 3;
        relativeEnemySize = relativeEnemySize < 0 ? relativeEnemySize * 10 : relativeEnemySize * 20;
        int relativeRange = 2 - range;
        relativeRange = relativeRange < 0 ? relativeRange * 10 : relativeRange * 20;
        viewModel.setValue(limitToRange(relativeEnemySize + relativeRange, -30, 60));
    }
    int limitToRange(int value, int min, int max) {
        return Math.max(Math.min(max, value), min);
    }
}
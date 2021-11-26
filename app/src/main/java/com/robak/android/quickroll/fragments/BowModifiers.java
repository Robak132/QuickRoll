package com.robak.android.quickroll.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.R;
import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ObservableModifier;

public class BowModifiers extends FragmentWithTools {
    private ObservableModifier observableModifier;

    int range = 2;
    int enemySize = 3;
    int obstacle = 0;
    int group = 0;
    int fear = 0;
    int aim = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bow_modifiers, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireParentFragment()).get(ObservableModifier.class);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            ImageView imageView = getImageViewByName(view, "range" + i);
            imageView.setOnClickListener(v -> {
                if (range != finalI) {
                    setImageColor(view, "range" + range, R.color.black);
                    range = finalI;
                    setImageColor(view, "range" + range, R.color.purple_primary);
                    updateModifier();
                }
            });
        }
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            ImageView imageView = getImageViewByName(view, "size" + i);
            imageView.setOnClickListener(v -> {
                if (enemySize != finalI) {
                    setImageColor(view, "size" + enemySize, R.color.black);
                    enemySize = finalI;
                    setImageColor(view, "size" + enemySize, R.color.purple_primary);
                    updateModifier();
                }
            });
        }
        for (int i = 1; i < 4; i++) {
            int finalI = i;
            ImageView imageView = getImageViewByName(view, "obstacle" + i);
            imageView.setOnClickListener(v -> {
                if (obstacle != 0) {
                    setImageColor(view, "obstacle" + obstacle, R.color.black);
                }
                obstacle = obstacle == finalI ? 0 : finalI;
                if (obstacle != 0) {
                    setImageColor(view, "obstacle" + obstacle, R.color.purple_primary);
                }
                updateModifier();
            });
        }
        for (int i = 1; i < 4; i++) {
            int finalI = i;
            ImageView imageView = getImageViewByName(view, "group" + i);
            imageView.setOnClickListener(v -> {
                if (group != 0) {
                    setImageColor(view, "group" + group, R.color.black);
                }
                group = group == finalI ? 0 : finalI;
                if (group != 0) {
                    setImageColor(view, "group" + group, R.color.purple_primary);
                }
                updateModifier();
            });
        }

        ImageView fearImageView = view.findViewById(R.id.fear);
        fearImageView.setOnClickListener(v -> {
            int color = fear == 1 ? R.color.black : R.color.purple_primary;
            setImageColor(fearImageView, color);
            fear = 1 - fear;
            updateModifier();
        });

        ImageView aimView = view.findViewById(R.id.aim);
        aimView.setOnClickListener(v -> {
            int color = aim == 1 ? R.color.black : R.color.purple_primary;
            setImageColor(aimView, color);
            aim = 1 - aim;
            updateModifier();
        });
    }

    void updateModifier() {
        observableModifier.setModifier(getModifier());
        observableModifier.setSLModifier(getSLModifier());
    }
    int getModifier() {
        int enemySizeMod = enemySize - 3;
        enemySizeMod = enemySizeMod < 0 ? enemySizeMod * 10 : enemySizeMod * 20;
        int rangeMod = 2 - range;
        rangeMod = rangeMod < 0 ? rangeMod * 10 : rangeMod * 20;
        int obstacleMod = obstacle * (-10);
        int groupMod = group * 20;
        int aimMod = aim * (-20);

        int totalModifier = enemySizeMod + obstacleMod + rangeMod + groupMod + aimMod;
        return limitToRange(totalModifier, -30, 60);
    }
    int getSLModifier() {
        return fear == 0 ? 0 : -1;
    }
}
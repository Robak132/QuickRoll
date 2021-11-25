package com.robak.android.quickroll;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ObservableModifier;

public class BowModifiers extends FragmentWithTools {
    View view;
    private ObservableModifier observableModifier;

    int range = 2;
    int enemySize = 3;
    int obstacle = 0;
    int fear = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bow_modifiers, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observableModifier = new ViewModelProvider(requireActivity()).get(ObservableModifier.class);

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

        ImageView imageView = view.findViewById(R.id.fear);
        imageView.setOnClickListener(v -> {
            int color = fear == 1 ? R.color.black : R.color.purple_primary;
            setImageColor(imageView, color);
            fear = 1 - fear;
            updateModifier();
            Log.d("LOG", String.valueOf(fear));
        });
    }

    void updateModifier() {
        observableModifier.setModifier(getModifier());
        observableModifier.setSLModifier(getSLModifier());
    }
    int getModifier() {
        int relativeEnemySize = enemySize - 3;
        relativeEnemySize = relativeEnemySize < 0 ? relativeEnemySize * 10 : relativeEnemySize * 20;
        int relativeRange = 2 - range;
        relativeRange = relativeRange < 0 ? relativeRange * 10 : relativeRange * 20;
        int relativeObstacle = obstacle * (-10);

        int totalModifier = relativeEnemySize + relativeObstacle + relativeRange;
        return limitToRange(totalModifier, -30, 60);
    }
    int getSLModifier() {
        return fear == 0 ? 0 : -1;
    }
}
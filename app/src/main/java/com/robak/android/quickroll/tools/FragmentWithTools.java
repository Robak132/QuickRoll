package com.robak.android.quickroll.tools;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.robak.android.quickroll.R;

import java.util.concurrent.atomic.AtomicReference;

public abstract class FragmentWithTools extends Fragment {
    protected ObservableModifier observableModifier = new ObservableModifier();
    protected View view;

    protected ImageView getImageViewByTag(View view, String tag) {
        return view.findViewWithTag(tag);
    }
    protected int getDrawableByName(String name) {
        return getResources().getIdentifier(name , "drawable", getActivity().getPackageName());
    }
    protected void setImageColorByTag(View view, String tag, int color) {
        try {
            getImageViewByTag(view, tag).setColorFilter(getActivity().getColor(color));
        } catch (Exception e) {
            Log.e("ERR", "Color can't be changed");
        }
    }
    protected Fragment createFragment(Fragment fragment, int frame) {
        FragmentManager childFragMan = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();
        childFragTrans.add(frame, fragment);
        childFragTrans.commit();
        return fragment;
    }
    protected int limitToRange(int value, int min, int max) {
        return Math.max(Math.min(max, value), min);
    }

    protected void addImageViewSeries(ConstraintLayout parent, String basename, int end, boolean turnOff, AtomicReference<Integer> pointer) {
        addImageViewSeries(parent, basename, 0, end, turnOff, pointer);
    }
    protected void addImageViewSeries(ConstraintLayout parent, String basename, int begin, int end, boolean turnOff, AtomicReference<Integer> pointer) {
        for (int i = begin; i <= end; i++) {
            int finalI = i;
            ImageView childView = createImageView(basename + i);
            childView.setImageResource(getDrawableByName("ic_" + basename + i));
            childView.setOnClickListener(v -> {
                boolean clickedNonActive = pointer.get() != finalI;
                if (clickedNonActive || turnOff) {
                    setImageColorByTag(view, basename + pointer.get(), R.color.black);
                    pointer.set(clickedNonActive ? finalI : 0);
                    setImageColorByTag(view, basename + pointer.get(), R.color.purple_primary);
                }
                updateModifier();
            });
            parent.addView(childView);
        }
    }
    protected void addImageView(ConstraintLayout parent, String basename, AtomicReference<Integer> pointer) {
        ImageView childView = createImageView(basename);
        childView.setImageResource(getDrawableByName("ic_" + basename));
        childView.setOnClickListener(v -> {
            int color = pointer.get() == 1 ? R.color.black : R.color.purple_primary;
            setImageColorByTag(view, basename, color);
            pointer.set(1 - pointer.get());
            updateModifier();
        });
        parent.addView(childView);
    }
    protected ImageView createImageView(String tag) {
        ImageView childView = new ImageView(getContext());
        childView.setId(View.generateViewId());
        childView.setTag(tag);
        return childView;
    }
    protected void setupConstraints(ConstraintLayout parent) {
        ConstraintSet set = new ConstraintSet();
        for (int i = 0; i < parent.getChildCount(); i++) {
            set.clone(parent);
            set.connect(parent.getChildAt(i).getId(), ConstraintSet.TOP, parent.getId(), ConstraintSet.TOP, 0);
            if (i > 0) {
                set.connect(parent.getChildAt(i).getId(), ConstraintSet.LEFT, parent.getChildAt(i - 1).getId(), ConstraintSet.RIGHT, 0);
            } else {
                set.connect(parent.getChildAt(i).getId(), ConstraintSet.LEFT, parent.getId(), ConstraintSet.LEFT, 0);
            }
            if (i < parent.getChildCount() - 1) {
                set.connect(parent.getChildAt(i).getId(), ConstraintSet.RIGHT, parent.getChildAt(i + 1).getId(), ConstraintSet.LEFT, 0);
            } else {
                set.connect(parent.getChildAt(i).getId(), ConstraintSet.RIGHT, parent.getId(), ConstraintSet.RIGHT, 0);
            }
            set.applyTo(parent);
        }
    }

    protected void updateModifier() {
        observableModifier.setModifier(getModifier());
        observableModifier.setSLModifier(getSLModifier());
    }
    protected abstract int getModifier();
    protected abstract int getSLModifier();
}
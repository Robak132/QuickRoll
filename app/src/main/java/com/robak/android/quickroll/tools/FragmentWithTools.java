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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class FragmentWithTools extends Fragment {
    protected ObservableModifier observableModifier = new ObservableModifier();
    protected View view;

    protected static final int HORIZONTAL = 0;
    protected static final int VERTICAL = 1;

    protected ImageView getImageViewByTag(View view, String tag) {
        return view.findViewWithTag(tag);
    }
    protected int getDrawableByName(String name) {
        return getResources().getIdentifier(name , "drawable", getActivity().getPackageName());
    }
    protected void changeColor(ConstraintLayout parent, int color) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            ((ImageView) parent.getChildAt(i)).setColorFilter(getActivity().getColor(color));
        }
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

    protected List<ImageView> addImageViewSeries(ConstraintLayout parent, String basename, int end, boolean turnOff, AtomicReference<Integer> pointer) {
        List<ImageView> viewList = new ArrayList<>();
        int begin = turnOff ? 1 : 0;

        for (int i = begin; i <= end; i++) {
            int finalI = i;
            ImageView childView = createImageView();
            childView.setImageResource(getDrawableByName("ic_" + basename + i));
            childView.setOnClickListener(v -> {
                boolean clickedNonActive = pointer.get() != finalI;
                if (clickedNonActive || turnOff) {
                    changeColor(parent, R.color.black);
                    if (clickedNonActive) {
                        pointer.set(finalI);
                        childView.setColorFilter(getActivity().getColor(R.color.purple_primary));
                    }
                    else {
                        pointer.set(0);
                    }
                }
                updateModifier();
            });
            parent.addView(childView);
            viewList.add(childView);
        }
        return viewList;
    }
    protected void addImageView(ConstraintLayout parent, String basename, AtomicReference<Integer> pointer) {
        ImageView childView = createImageView();
        childView.setImageResource(getDrawableByName("ic_" + basename));
        childView.setOnClickListener(v -> {
            int color = pointer.get() == 1 ? R.color.black : R.color.purple_primary;
            setImageColorByTag(view, basename, color);
            pointer.set(1 - pointer.get());
            updateModifier();
        });
        parent.addView(childView);
    }
    protected ImageView createImageView() {
        ImageView childView = new ImageView(getContext());
        childView.setId(View.generateViewId());
        return childView;
    }
    protected void setupConstraints(ConstraintLayout parent) {
        setupConstraints(parent, HORIZONTAL);
    }
    protected void setupConstraints(ConstraintLayout parent, int alignment) {
        int const1 = alignment == 0 ? ConstraintSet.LEFT   : ConstraintSet.TOP;
        int const2 = alignment == 0 ? ConstraintSet.RIGHT  : ConstraintSet.BOTTOM;
        int const3 = alignment == 0 ? ConstraintSet.TOP    : ConstraintSet.LEFT;
        int const4 = alignment == 0 ? ConstraintSet.BOTTOM : ConstraintSet.RIGHT;

        ConstraintSet set = new ConstraintSet();
        for (int i = 0; i < parent.getChildCount(); i++) {
            set.clone(parent);
            set.connect(parent.getChildAt(i).getId(), const3, parent.getId(), const3, 0);
            set.connect(parent.getChildAt(i).getId(), const4, parent.getId(), const4, 0);
            if (i > 0) {
                set.connect(parent.getChildAt(i).getId(), const1, parent.getChildAt(i - 1).getId(), const2, 0);
            } else {
                set.connect(parent.getChildAt(i).getId(), const1, parent.getId(), const1, 0);
            }
            if (i < parent.getChildCount() - 1) {
                set.connect(parent.getChildAt(i).getId(), const2, parent.getChildAt(i + 1).getId(), const1, 0);
            } else {
                set.connect(parent.getChildAt(i).getId(), const2, parent.getId(), const2, 0);
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
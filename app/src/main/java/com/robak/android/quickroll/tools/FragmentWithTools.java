package com.robak.android.quickroll.tools;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FragmentWithTools extends Fragment {
    protected ImageView getImageViewByName(View view, String name) {
        int id = getResources().getIdentifier(name, "id", getActivity().getPackageName());
        return view.findViewById(id);
    }
    protected void setImageColor(View view, String name, int color) {
        getImageViewByName(view, name).setColorFilter(getActivity().getColor(color));
    }
    protected void setImageColor(ImageView imageView, int color) {
        imageView.setColorFilter(getActivity().getColor(color));
    }

    protected int limitToRange(int value, int min, int max) {
        return Math.max(Math.min(max, value), min);
    }
}
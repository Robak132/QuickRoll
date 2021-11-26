package com.robak.android.quickroll.tools;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
}
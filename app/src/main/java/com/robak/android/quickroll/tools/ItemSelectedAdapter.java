package com.robak.android.quickroll.tools;

import android.view.View;
import android.widget.AdapterView;

public interface ItemSelectedAdapter extends AdapterView.OnItemSelectedListener {
    void select(AdapterView<?> parent, View view, int position, long id);

    default void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        select(parent, view, position, id);
    }
    default void onNothingSelected(AdapterView<?> parent) {
        // Ignored
    }
}
